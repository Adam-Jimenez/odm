package query;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import dao.singleton.DbSingleton;
import reflection.Instantiator;

public class Selector {

	private Class<?> classType;

	public Selector(Class<?> returnClassType) {
		super();
		this.classType = returnClassType;
	}

	
	public Object get() {
		return get(null);
	}
	/**
	 * Get given class from db and create instance
	 * 
	 * @param filters Selector simply wraps the filters of the mongodb driver
	 * see: http://api.mongodb.com/java/3.0/?com/mongodb/client/model/Filters.html	
	 * 
	 * @return Newly create instance with data from db
	 */
	public Object get(Bson filters) {
		try {
			MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection(classType.getCanonicalName());
			Document document; 
			if (filters == null) {
				document = collection.find().first();
			} else {
				document = collection.find(filters).first();
			}
			/*
			 * No result
			 */
			if (document == null) {
				return null;
			}
			
			Object newInstance = Instantiator.instantiateFromDocument(document, classType);
			return newInstance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object[] getAll() {
		return getAll(null);
	}
	/**
	 * Get given class from db matching filter and create instances
	 * 
	 * @param filters Selector simply wraps the filters of the mongodb driver
	 * see: http://api.mongodb.com/java/3.0/?com/mongodb/client/model/Filters.html	
	 * 
	 * @return Newly create instances array with data from db
	 */
	public Object[] getAll(Bson filters) {
		try {
			MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection(classType.getCanonicalName());
			MongoCursor<Document> documentsIterator = null;
			if (filters == null) {
				documentsIterator = collection.find().iterator();
			} else {
				documentsIterator = collection.find(filters).iterator();
			}
			/*
			 * No result
			 */
			if (documentsIterator == null) {
				return null;
			}
			return Instantiator.instantiateFromDocuments(documentsIterator, classType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
