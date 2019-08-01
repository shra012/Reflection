package methodhandle.api;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.LoggingConfigure;
import presistance.pojos.Person;

public class PlayWithMethodHandle {
	private static final Logger LOGGER = LoggingConfigure.getLogger(PlayWithMethodHandle.class);
	public static void main(String[] args) {
		LOGGER.log(Level.FINE, "Entering Class "+PlayWithMethodHandle.class.getSimpleName()+" main method...");
		try {
			Lookup lookup = MethodHandles.lookup();
			MethodType emptyConstructorMethodType = MethodType.methodType(void.class);
			MethodHandle emptyPersonConstructor = lookup.findConstructor(Person.class, emptyConstructorMethodType);
			Person person = (Person)emptyPersonConstructor.invoke();
			System.out.println(person);
			MethodType constructorMethodType = MethodType.methodType(void.class,String.class,int.class);
			MethodHandle PersonConstructor = lookup.findConstructor(Person.class, constructorMethodType);
			Person shravan = (Person)PersonConstructor.invoke("Shravan",25);
			System.out.println(shravan);
		} catch (Throwable e) {
			LOGGER.log(Level.FINER, "Exception Occured in Class "+PlayWithMethodHandle.class.getSimpleName()+" main method...",e);
			throw new RuntimeException(e);
		}
		LOGGER.log(Level.FINE, "Leaving Class "+PlayWithMethodHandle.class.getSimpleName()+" main method...");
	}
}
