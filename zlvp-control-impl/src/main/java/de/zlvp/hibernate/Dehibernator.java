package de.zlvp.hibernate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;

public class Dehibernator {

	public static void dehibernate(Object o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DehibernatingObjectOutputStream oos = new DehibernatingObjectOutputStream(baos);
			oos.writeObject(o);
			oos.close();
			o = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())).readObject();
		} catch (Exception e) {
			// TODO ???
		}
	}

}
