package reflection.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
		return primitives;
	}
	/**
	 * Returns all fields that were directly declared or inherited for a class
	 * NOTE: breaks iteration if the extended class is a primitive, we don't want to inherit that junk
	 * NOTE: ignores static fields
	 * @param objectClass The class
	 * @return List of fields
	 */
	public static List<Field> getInheritedFields(Class<?> objectClass) {
		List<Field> fieldsInObject = new ArrayList<Field>();
		do {
			Field[] currentClassLevelFields = objectClass.getDeclaredFields();
			for ( Field field : currentClassLevelFields) {
				if (!Modifier.isStatic(field.getModifiers())) {
					fieldsInObject.add(field);
			    }	
			}
			objectClass = objectClass.getSuperclass();
		} while (objectClass != null && !isPrimitive(objectClass));
		return fieldsInObject;
	}
	
	public static boolean isMultidimensionalArray(Object object) {
		return object.getClass().getComponentType().isArray();
	}
	
	/**
	 * Iterate all dimensions of array to find if root child is primitive type
	 * @param object The array
	 * @return if it contains primitive types
	 */
	public static boolean isPrimitiveMultidimensionalArray(Object object) {
		Class<?> elementClass = object.getClass();
		while(elementClass.getComponentType() != null) {
			elementClass = elementClass.getComponentType();
		}
		return isPrimitive(elementClass);
	}
	
	/**
	 * Transforms a multi dimensional array into a multi dimensional list
	 * @param object The multi dimensional array to transform
	 * @return Multidimensional List
	 */
	public static List<?> multiDimensionalArrayToMultiDimensionalList(Object object) {
		if(!object.getClass().isArray()) {
			return new ArrayList<Object>();
		}
		List<Object> list = new ArrayList<Object>();
		Object[] objectArray = (Object[]) object;
		for(Object childObject : objectArray) {
			if (!childObject.getClass().isArray()) {
				list.add(childObject);
			} else {
				list.add(multiDimensionalArrayToMultiDimensionalList(childObject));
			}
		}
		return list;
	}
	
	/**
	 * Guess the name of the getter based on camel case style conventions
	 * assumes the field was already in camelCase TODO: use external library to
	 * support other cases
	 * 
	 * ex: age yields getAge, but TELEPHONE_NUMBER will fail
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @return the guessed getterName
	 */
	public static String getGetterNameForFieldName(String fieldName) {
		String getterNameForField = new StringBuilder().append("get").append(Character.toUpperCase(fieldName.charAt(0)))
				.append(fieldName.substring(1)).toString();
		return getterNameForField;
	}

	// TODO: merge this with getGetterNameForFieldName
	public static String getSetterNameForFieldName(String fieldName) {
		String getterNameForField = new StringBuilder().append("set").append(Character.toUpperCase(fieldName.charAt(0)))
				.append(fieldName.substring(1)).toString();
		return getterNameForField;
	}
	
	/**
	 * Invokes the given method for the given object and returns the
	 * value
	 * 
	 * @param dataObject
	 *            The method context
	 * @param method
	 *            The method
	 * @return The value returned from the method
	 * @throws Exception
	 */
	public static Object invokeMethod(Object dataObject, Method method) throws Exception {
		Object returnValue;
		returnValue = method.invoke(dataObject);
		return returnValue;
	}
	
	/**
	 * Gets the getter
	 * 
	 * @param objectClass
	 *            The class we want to fetch the getter from
	 * @param getterName
	 * @return The getter method
	 * @throws Exception
	 */
	public static Method getGetter(Class<?> objectClass, String getterName) throws Exception {
		Method getterMethod = null;
		try {
			getterMethod = objectClass.getDeclaredMethod(getterName);

		} catch (NoSuchMethodException e) {
			/*
			 * If we didn't find getter in current class, look into superclasses
			 * (if they exist)
			 */
			if (objectClass.getSuperclass() != null) {
				getterMethod = getGetter(objectClass.getSuperclass(), getterName);
			} else {
				System.err.println(
						"Oups, the getter " + getterName + " isn't set in class " + objectClass.getCanonicalName());
				throw new Exception(e);
			}
		} catch (SecurityException e) {
			System.err.println(
					"Oups, the getter " + getterName + " isn't accessible in class " + objectClass.getCanonicalName());
			throw new Exception(e);
		}
		return getterMethod;
	}
	
	// TODO: merge this with getGetter
	public static Method getSetter(Class<?> objectClass, String setterName, Class<?> setterType) throws Exception {
		Method setterMethod = null;
		try {
			setterMethod = objectClass.getDeclaredMethod(setterName, setterType);

		} catch (NoSuchMethodException e) {
			/*
			 * If we didn't find setter in current class, look into superclasses
			 * (if they exist)
			 */
			if (objectClass.getSuperclass() != null) {
				setterMethod = getSetter(objectClass.getSuperclass(), setterName, setterType);
			} else {
				System.err.println(
						"Oups, the setter " + setterName + " isn't set in class " + objectClass.getCanonicalName());
				throw new Exception(e);
			}
		} catch (SecurityException e) {
			System.err.println(
					"Oups, the setter " + setterName + " isn't accessible in class " + objectClass.getCanonicalName());
			throw new Exception(e);
		}
		return setterMethod;
	}
}
