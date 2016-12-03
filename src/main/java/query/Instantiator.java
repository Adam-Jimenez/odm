package query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCursor;

import reflection.utils.ReflectionUtils;

public class Instantiator {

	/**
	 * Create an object instance from a document
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
			resultingInstances.add(newInstance);
	    }
		return resultingInstances.toArray(new Object[resultingInstances.size()]);
	}
}
