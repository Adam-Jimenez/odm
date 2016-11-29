package dao.singleton;

import java.util.HashMap;

import com.mongodb.client.MongoDatabase;

import dao.exceptions.DbNotFound;

public final class DbSingleton {
	
	/**
	 * Hashmap of databases identified by their names
	 */
	private final static HashMap<String, MongoDatabase> singletonDbs = new HashMap<String, MongoDatabase>();

	private DbSingleton() {
		
	}
	
	/**
	 * fetch a db object using it's name
	 * @param dbName The name of the db
	 * @return DB object for interacting with it
	 * @throws DbNotFound if it doesn't exist
	 */
	public static MongoDatabase getDB (String dbName) throws DbNotFound {
		// Look if db is already fetched
		MongoDatabase db = singletonDbs.get(dbName);
		if(db == null) {
			// if not, load using client
			db = ClientSingleton.getClient().getDatabase(dbName);
			if (db == null) {
				throw new DbNotFound();
			} else {
				singletonDbs.put(dbName, db);
			}
		}
		return db;
	}
	
	/**
	 * Fetch the default db object defined in properties
	 * TODO: use properties
	 * @throws DbNotFound if it doesn't exist
	 */
	public static MongoDatabase getDefaultDB() throws DbNotFound {
		return getDB("mongo2");
	}
}
