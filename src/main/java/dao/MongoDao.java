package dao;

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
			System.err.println(e.getMessage());
			System.err.println("=============");
			e.printStackTrace();
		}
	}
}
