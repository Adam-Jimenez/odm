package query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

import reflection.utils.ReflectionUtils;

public class Instantiator {

	private Class<?> returnClassType;
	
	public Instantiator(Class<?> returnClassType) {
		super();
		this.returnClassType = returnClassType;
	}

	/**
	 * Create an object instance from a document
	 * @param document Document containing object data
	 * @return New object instance
	 */
	public Object instanciateFromDocument(Document document) {
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
					String setterName = ReflectionUtils.getSetterNameForFieldName(fieldName);
					Method setter = ReflectionUtils.getSetter(returnClassType, setterName, field.getType());
					setter.invoke(newInstance, document.get(fieldName));
				}
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
	
	public Object[] instantiateFromDocuments(MongoCursor<Document> cursor) {
		List<Object> resultingInstances = new ArrayList<Object>();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			Object newInstance = instanciateFromDocument(document);
			resultingInstances.add(newInstance);
	    }
		return resultingInstances.toArray(new Object[resultingInstances.size()]);
	}
}
