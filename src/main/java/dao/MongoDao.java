package dao;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import dao.singleton.DbSingleton;
import query.CachedObjects;
import reflection.Reflector;

public class MongoDao {

	public static void insert(Object dataObject) {
		if (dataObject == null) {
			return;
		}
		String collectionNameFromObjectClass = dataObject.getClass().getCanonicalName();
		/*
		 * get the collection of the same name as the class as the object (we
		 * store all instances of the same class in the same collection)
		 */
		try {
			// transform object into document
			Document document = Reflector.documentFromObject(dataObject);
			// fetch collection
			MongoCollection<Document> collection = DbSingleton.getDefaultDB()
					.getCollection(collectionNameFromObjectClass);
			// and insert it document in collection
			collection.insertOne(document);

			ObjectId id = document.getObjectId("_id");
			CachedObjects.addReference(dataObject, id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("=================================");
			System.err.println("Error message: " + e.getMessage());
		}
	}

	public static void insertMany(List<?> dataObject) {
		if (dataObject.size() < 1) {
			return;
		}
		/*
		 * Assume all objects in list are same type
		 */
		String collectionNameFromObjectClass = dataObject.get(0).getClass().getCanonicalName();
		/*
		 * get the collection of the same name as the class as the object (we
		 * store all instances of the same class in the same collection)
		 */
		try {
			// transform object into document
			List<Document> documents = Reflector.documentsFromObjectArray(dataObject.toArray());
			// fetch collection
			MongoCollection<Document> collection = DbSingleton.getDefaultDB()
					.getCollection(collectionNameFromObjectClass);
			// and insert it document in collection
			collection.insertMany(documents);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("=================================");
			System.err.println("Error message: " + e.getMessage());
		}
	}

	public static void update(Object dataObject) {
		/*
		 * dataObject needs to be inserted in or read from the database in order
		 * to be updated
		 */
		if (dataObject == null || !CachedObjects.hasReference(dataObject)) {
			return;
		}
		String collectionNameFromObjectClass = dataObject.getClass().getCanonicalName();
		/*
		 * get the collection of the same name as the class as the object (we
		 * store all instances of the same class in the same collection)
		 */
		try {
			// transform object into document
			Document document = Reflector.documentFromObject(dataObject);
			// needed for update, see http://stackoverflow.com/a/29446870
			Document updateDocument = new Document("$set", document);
			// fetch collection
			MongoCollection<Document> collection = DbSingleton.getDefaultDB()
					.getCollection(collectionNameFromObjectClass);
			// get its database id
			ObjectId id = CachedObjects.getReference(dataObject);
			// create filter by objectId
			BasicDBObject searchQuery = new BasicDBObject().append("_id", id);

			collection.updateOne(searchQuery, updateDocument);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("=================================");
			System.err.println("Error message: " + e.getMessage());
		}
	}
}
