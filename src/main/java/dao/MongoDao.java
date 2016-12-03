package dao;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import dao.singleton.DbSingleton;
import reflection.Reflector;

public class MongoDao {

	public static void insert(Object dataObject) {
		String collectionNameFromObjectClass = dataObject.getClass().getCanonicalName();
		/*
		 *  get the collection of the same name as the class as the object
		 *	(we store all instances of the same class in the same collection) 
		 */
		try {
			// transform object into document 
			Document document = Reflector.documentFromObject(dataObject);
			// fetch collection
			MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection(collectionNameFromObjectClass);
			// and insert it document in collection
			collection.insertOne(document);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("=================================");
			System.err.println("Error message: " + e.getMessage());
		}
	}
	
	public static void insertMany(List<?> dataObject) {
		if (dataObject.size() < 1) return;
		/*
		 * Assume all objects in list are same type
		 */
		String collectionNameFromObjectClass = dataObject.get(0).getClass().getCanonicalName();
		/*
		 *  get the collection of the same name as the class as the object
		 *	(we store all instances of the same class in the same collection) 
		 */
		try {
			// transform object into document 
			List<Document> documents = Reflector.documentsFromObjectArray(dataObject.toArray());
			// fetch collection
			MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection(collectionNameFromObjectClass);
			// and insert it document in collection
			collection.insertMany(documents);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("=================================");
			System.err.println("Error message: " + e.getMessage());
		}
	}
}
