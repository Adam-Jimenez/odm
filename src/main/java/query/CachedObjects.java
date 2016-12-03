package query;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

/**
 * Everytime documents are fetched from the db
 * we keep a reference to both the object and the db object Id
 * in a map wrapped in this singleton
 * 
 * That way, we can update and delete object and their document-equivalent easily
 * 
 * @author adam
 *
 */
public final class CachedObjects {

	/*
	 * Key: mongo-db objectId
	 * Value: reference to the object representation of database data
	 */
	private static Map<Object, ObjectId> objectsByObjectId;

	private CachedObjects() {
		
	}
	
	public synchronized static Map<Object, ObjectId> getInstance() {
		if(objectsByObjectId == null) {
			objectsByObjectId = new HashMap<Object, ObjectId>();
		}
		return objectsByObjectId;
	}
	
	public static void addReference(Object object, ObjectId id) {
		getInstance().put(object, id);
	}
	
	public static boolean hasReference(Object object) {
		return getInstance().containsKey(object);
	}

	public static void removeReference(Object object){
		getInstance().remove(object);
	}
	
	public static ObjectId getReference(Object object) {
		return getInstance().get(object);
	}
}
