package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import reflection.utils.ReflectionUtils;

public class Reflector {
	/**
	 * Takes an object and parses the field it contains, gets their value and
	 * builds a document with it
	 * 
	 * This assume the given class isn't a private inner class
	 * in the following case a field is added by default, $this0
	 * and from this may result unexpected state.
	 * 
	 * @param dataObject
	 *            The object we want to store in mongoDb
	 * @return the given object in document format
	 * @throws Exception 
	 */
	public static Document documentFromObject(Object dataObject) throws Exception {
		Document document = new Document();
		Class<?> objectClass = dataObject.getClass();
		Field[] fieldsInObject = objectClass.getDeclaredFields();
		
		for (Field field : fieldsInObject) {
			String fieldName = field.getName();
			String getterName = getGetterNameForFieldName(fieldName);
			Method getter = getGetter(objectClass, getterName);
			Object fieldValue = invokeGetter(dataObject, getter);
			/*
			 *  we can append primitive types and arrays directly to the document
			 *  otherwise we need to do a recursive call
			 *  TODO: extract the following into a recursive function
			 */
			if (ReflectionUtils.isPrimitive(fieldValue.getClass())) {
				document.append(fieldName, fieldValue);
			} else if (ReflectionUtils.isPrimitiveArray(fieldValue.getClass())) {
				Object[] primitiveValues = (Object[]) fieldValue;
				List<Object> primitiveList = Arrays.asList(primitiveValues);
				document.append(fieldName, primitiveList);
			} else {
				document.append(fieldName, documentFromObject(fieldValue));
			}
		}

		return document;
	}

	/**
	 * Invokes the given getter method for the given object and returns the value
	 * @param dataObject The object to get from
	 * @param getter The getter method
	 * @return The value returned from getter
	 * @throws Exception
	 */
	private static Object invokeGetter(Object dataObject, Method getter) throws Exception {
		Object fieldValue;
		fieldValue = getter.invoke(dataObject);
		return fieldValue;
	}

	/**
	 * Gets the getter
	 * @param objectClass The class we want to fetch the getter from
	 * @param getterName
	 * @return The getter method
	 * @throws Exception 
	 */
	private static Method getGetter(Class<?> objectClass, String getterName) throws Exception {
		Method getterMethod = null;
		try {
			getterMethod = objectClass.getDeclaredMethod(getterName);
		} catch (NoSuchMethodException e) {
			System.err.println("Oups, the getter " + getterName +
					" isn't set in class " + objectClass.getCanonicalName());
			throw new Exception(e);
		} catch (SecurityException e) {
			System.err.println("Oups, the getter " + getterName + 
					" isn't accessible in class " + objectClass.getCanonicalName());
			throw new Exception(e);
		}
		return getterMethod;
	}

	/**
	 * Guess the name of the getter based on camel case style conventions 
	 * assumes the field was already in camelCase
	 * TODO: use external library to support other cases
	 * 
	 * ex: age yields getAge, but TELEPHONE_NUMBER will fail
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @return the guessed getterName
	 */
	private static String getGetterNameForFieldName(String fieldName) {
		String getterNameForField = new StringBuilder()
				.append("get")
				.append(Character.toUpperCase(fieldName.charAt(0)))
				.append(fieldName.substring(1))
				.toString();
		return getterNameForField;
	}

}
