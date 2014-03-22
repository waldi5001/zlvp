package de.zlvp.hibernate;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.pojo.javassist.SerializableProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ObjectOutputStream}, der beim Schreiben von Objekten alle
 * Hibernate-spezifischen Objekte und Proxies entfernt. Dabei werden noch nicht
 * initialisierte Relationen auf <code>null</code> gesetzt.
 */
public class DehibernatingObjectOutputStream extends ObjectOutputStream {

	private static final Logger log = LoggerFactory.getLogger(DehibernatingObjectOutputStream.class);

	public DehibernatingObjectOutputStream(OutputStream out) throws IOException {
		super(out);
		enableReplaceObject(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Object replaceObject(Object obj) throws IOException {
		Object result = obj;
		// Hibernate replaces HibernateProxy objects with SerializableProxy
		// object during serialization (overriding writeReplace()).
		// It does that only, if the object has not yet been initialized, so
		// they can be safely removed.
		if (obj instanceof SerializableProxy) {
			result = null;
		} else if (obj instanceof PersistentCollection) {
			PersistentCollection collection = (PersistentCollection) obj;
			if (!collection.wasInitialized()) {
				result = null;
			} else {
				if (obj instanceof List) {
					result = new ArrayList((List) obj);
				} else if (obj instanceof Set) {
					result = new HashSet((Set) obj);
				} else {
					log.warn("unhandled hibernate proxy collection " + obj.getClass());
				}
			}
		}
		if (log.isTraceEnabled()) {
			if (result != obj) {
				if (result == null) {
					log.trace("removing " + obj.getClass());
				} else {
					log.trace("replacing " + obj.getClass() + " with " + result.getClass());
				}
			} else {
				log.trace("keeping " + result.getClass());
			}
		}
		return result;
	}

}