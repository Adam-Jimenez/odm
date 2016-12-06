package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import dao.singleton.CachedObjects;
import query.utils.QueryUtils;
import reflection.utils.ReflectionUtils;

public class Instantiator {

	/**
	 * Create an object instance from a document
	 * NOTE: the logic of this looks a lot like the one in reflector
	 * though i can't figure out how to reuse it
	 * 
	 * @param document Document containing object data
	 * @return New object instance
	 */
	public static Object instantiateFromDocument(Document document, Class<?> returnClassType) {
		if (document == null) {
			return null;
		}
		try {
			Object newInstance = returnClassType.newInstance();
			/*
			 * We iterate through the fields of the object
			 * we want to instantiate, looking if we can find it's value in 
			 * the document to set it afterwards
			 */
			List<Field> fieldsInObject = ReflectionUtils.getInheritedFields(returnClassType);
			for(Field field : fieldsInObject) {
				if(document.containsKey(field.getName())) {
					String fieldName = field.getName();
					Class<?> fieldClass = field.getType();
					Object fieldValue = document.get(fieldName);
					String setterName = ReflectionUtils.getSetterNameForFieldName(fieldName);
					Method setter = ReflectionUtils.getSetter(returnClassType, setterName, field.getType());
					
					if (ReflectionUtils.isPrimitive(fieldValue.getClass())) {
						setter.invoke(newInstance, fieldValue);
					} else if(fieldValue instanceof Collection<?>){
						/*
						 * Convert fieldValue to list and set it
						 */
						Object[] values = ((Collection<?>) fieldValue).toArray();
						if (ReflectionUtils.isPrimitiveArray(values.getClass())) {
							setter.invoke(newInstance, Arrays.asList(values));
						} else {
							/*
							 * array of subobjects
							 */
							Document[] documents = QueryUtils.castResult(values, Document[].class);
							Type genericType = (((ParameterizedType)field.getGenericType()).getActualTypeArguments())[0];
							Class<?> classOfGeneric = Class.forName(genericType.getTypeName());
							setter.invoke(newInstance, Arrays.asList(instantiateFromDocuments(documents, classOfGeneric)));
						}
					} else {
						/*
						 * if field is object (document), load it recursively
						 */
						setter.invoke(newInstance, instantiateFromDocument((Document) fieldValue, fieldClass));
					}
				}
			}
			/*
			 * map object reference to its ObjectId and cache it for updates/deletes
			 */
			ObjectId id = document.getObjectId("_id");
			if (id != null) {
				CachedObjects.addReference(newInstance, id);
			}
			return newInstance;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("=======");
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public static Object[] instantiateFromDocuments(MongoCursor<Document> cursor, Class<?> returnClassType) {
		List<Object> resultingInstances = new ArrayList<Object>();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			Object newInstance = instantiateFromDocument(document, returnClassType);
			/*
			 * map object reference to its ObjectId and cache it for updates/deletes
			 */
			ObjectId id = document.getObjectId("_id");
			if (id != null) {
				CachedObjects.addReference(newInstance, id);
			}
			resultingInstances.add(newInstance);
	    }
		return resultingInstances.toArray(new Object[resultingInstances.size()]);
	}
	
	public static Object[] instantiateFromDocuments(Document[] documents, Class<?> returnClassType) {
		List<Object> resultingInstances = new ArrayList<Object>();
		for(Document document : documents) {
			Object newInstance = instantiateFromDocument(document, returnClassType);
			/*
			 * map object reference to its ObjectId and cache it for updates/deletes
			 */
			ObjectId id = document.getObjectId("_id");
			if (id != null) {
				CachedObjects.addReference(newInstance, id);
			}
			resultingInstances.add(newInstance);
	    }
		return resultingInstances.toArray(new Object[resultingInstances.size()]);
	}
}
