package de.zlvp.hibernate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DehibernatingInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DehibernatingObjectOutputStream oos = new DehibernatingObjectOutputStream(baos);
		oos.writeObject(invocation.proceed());
		oos.close();
		return new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())).readObject();
	}

}
