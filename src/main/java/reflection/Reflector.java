package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import reflection.utils.ReflectionUtils;

public class Reflector {

	/**
	 * Takes an object and parses the fields it contains, gets their value and
	 * builds a MongoDb document with it
	 * 
	 * This assume the given class isn't a private inner class in the following
	 * case a field is added by default, $this0 and from this may result
	 * unexpected state.
	 * 
	 * @param dataObject
	 *            The object we want to store in mongoDb
	 * @return the given object in document format
	 * @throws Exception
	 */
	public static Document documentFromObject(Object dataObject) throws Exception {
		Document document = new Document();
		Class<?> objectClass = dataObject.getClass();

		/*
		 * We need to iterate through superclasses to get inherited fields
		 */
		List<Field> fieldsInObject = ReflectionUtils.getInheritedFields(objectClass);

		for (Field field : fieldsInObject) {
			String fieldName = field.getName();
			String getterName = ReflectionUtils.getGetterNameForFieldName(fieldName);
			Method getter = ReflectionUtils.getGetter(objectClass, getterName);
			Object fieldValue = ReflectionUtils.invokeMethod(dataObject, getter);
			if (fieldValue == null) {
				continue;
			}
			Class<?> fieldClass = fieldValue.getClass();
			/*
			 * we can append primitive types and arrays directly to the document
			 * otherwise we need to do a recursive call 
			 */
			if (ReflectionUtils.isPrimitive(fieldClass)) {
				document.append(fieldName, fieldValue);
			} else if (fieldClass.isArray()) {

				/*
				 * Note: this line will fail with an array of primitive type eg:
				 * int, boolean, etc. Use wrapper objects instead: Integer, Boolean,
				 * etc.
				 */
				Object[] fieldComponentValues = (Object[]) fieldValue;
				
				if (ReflectionUtils.isPrimitiveArray(fieldClass)) {
					/*
					 * Document.append takes lists instead of array so we need
					 * to convert
					 */
					List<Object> primitiveList = Arrays.asList(fieldComponentValues);
					document.append(fieldName, primitiveList);
				/*
				 * Support for multidimensional arrays
				 */
				} else if (ReflectionUtils.isMultidimensionalArray(fieldComponentValues)) {
					if (ReflectionUtils.isPrimitiveMultidimensionalArray(fieldComponentValues)) {
						document.append(fieldName, ReflectionUtils.multiDimensionalArrayToMultiDimensionalList(fieldComponentValues));
					} else {
						//TODO: test this
						//document.append(fieldName, documentsFromObjectArray(fieldComponentValues));
					}
				} else {
					/*
					 * If we have array of unknown type, convert that type to
					 * Document through more reflection!
					 */
					document.append(fieldName, documentsFromObjectArray(fieldComponentValues));
				}
			} else if (fieldValue instanceof Collection<?>) {
				Object[] fieldComponentValues = ((Collection<?>) fieldValue).toArray();
				/*
				 * This lines gets which generic value is used in the collection above
				 * getActualTypeArguments return an array of generics types used, in our case,
				 * only one is used, so we can access index 0 directly
				 */
				Type genericType = (((ParameterizedType)field.getGenericType()).getActualTypeArguments())[0];
				Class<?> classOfGeneric = Class.forName(genericType.getTypeName());

				if (ReflectionUtils.isPrimitive(classOfGeneric)) {
					List<Object> primitiveList = Arrays.asList(fieldComponentValues);
					document.append(fieldName, primitiveList);
				} else {
					document.append(fieldName, documentsFromObjectArray(fieldComponentValues));
				}
			} else if (fieldValue instanceof Map<?, ?>) {
				/*
				 *  Set of entries
				 */
				Set<?> mapEntries =  ((Map<?, ?>)fieldValue).entrySet();
				/*
				 * Here we get both the types for the key and the values of the map
				 * NOTE: the key must be primitive
				 */
				Type genericKeyType = (((ParameterizedType)field.getGenericType()).getActualTypeArguments())[0];
				Type genericValueType = (((ParameterizedType)field.getGenericType()).getActualTypeArguments())[1];
				Class<?> classOfKey = Class.forName(genericKeyType.getTypeName());
				Class<?> classOfValue = Class.forName(genericValueType.getTypeName());
				if (!ReflectionUtils.isPrimitive(classOfKey)) {
					throw new Exception("Oups, tried to use object as key for field " + field.getName());
				}
				/*
				 * We iterate over the entries of the map and create a document
				 * that we will append to the main document
				 */
				Document mapSubDocument = new Document();
				for(Object entryObject: mapEntries) {
					Entry<?, ?> entry = (Entry<?, ?>) entryObject;
					/*
					 * If value is primitive, append directly, else recursive call
					 */
					if (ReflectionUtils.isPrimitive(classOfValue)) {
						mapSubDocument.append(String.valueOf(entry.getKey()), entry.getValue());
					} else {
						mapSubDocument.append(String.valueOf(entry.getKey()), documentFromObject(entry.getValue()));
					}
				}
				document.append(fieldName, mapSubDocument);
			} else {
				/*
				 * Unknown field type, apply recursion
				 */
				document.append(fieldName, documentFromObject(fieldValue));
			}
		}
		return document;
	}

	/**
	 * Companion recursive function to documentFromObject, except it treats
	 * arrays of objects
	 * 
	 * @param fieldComponentValues
	 *            The array of object values expected to be extract from
	 *            documentFromObject where the array was a field of a given
	 *            object
	 * @return A list of documents to be appended in a document
	 */
	public static List<Document> documentsFromObjectArray(Object[] fieldComponentValues) {
		List<Document> documentsFromObjectArray = new ArrayList<Document>();
		for (Object object : fieldComponentValues) {
			try {
				Document document = documentFromObject(object);
				documentsFromObjectArray.add(document);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return documentsFromObjectArray;
	}

	

	
	
}
