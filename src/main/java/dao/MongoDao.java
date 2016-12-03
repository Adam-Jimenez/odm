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
			// fetch collection
			MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection(collectionNameFromObjectClass);
			// transform object into document and insert it
			Document document = Reflector.documentFromObject(dataObject);
			collection.insertOne(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
