package reflection.utils;

import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {

	/**
	 * Tests whether a given class is one of the primitives type wrapper classes
	 * @param objectClass The class of the field to test
	 * @return if it is a primitive
	 */
	public static boolean isPrimitive(Class<?> objectClass) {
		return getPrimitiveTypes().contains(objectClass);
	}
	
	/**
	 * Tests whether the given class is an array of primitives
	 * @param objectClass The class to test
	 * @return if it is a primitive array
	 */
	public static boolean isPrimitiveArray(Class<?> objectClass) {
		return isPrimitive(objectClass.getComponentType());
	}

	/**
	 * @return The list of primitive types wrapper classes in Java
	 * Note: String is considered a primitive for our cause
	 */
	private static Set<Class<?>> getPrimitiveTypes() {
		Set<Class<?>> primitives = new HashSet<Class<?>>();
		primitives.add(Boolean.class);
		primitives.add(Character.class);
		primitives.add(Byte.class);
		primitives.add(Short.class);
		primitives.add(Integer.class);
		primitives.add(Long.class);
		primitives.add(Float.class);
		primitives.add(Double.class);
		primitives.add(String.class);
		primitives.add(Void.class);
		return primitives;
	}
}
