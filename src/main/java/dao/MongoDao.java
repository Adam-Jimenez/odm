package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import dao.exceptions.DbNotFound;
import dao.singleton.DbSingleton;

public class MongoDao {

	public static void helloMongo() throws DbNotFound{
		MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection("abscsa");
		Document document = new Document("name", "MongoDB")
				.append("type", "database")
				.append("count", 1)
				.append("info", new Document("x", 203).append("y", 102));
		collection.insertOne(document);
	}

	public static void main(String[] args) {
		try {
			helloMongo();
		} catch (DbNotFound e) {
			System.err.println("Shit, where's the database?");
			e.printStackTrace();
			System.exit(10000); // TODO: document exit codes(10000 is db not
								// found)
		}
	}
}
