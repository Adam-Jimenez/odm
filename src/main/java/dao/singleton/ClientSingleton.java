package dao.singleton;

import com.mongodb.MongoClient;

import properties.DbPropertiesSingleton;

public final class ClientSingleton {
	
	private static MongoClient singletonClient;

	private ClientSingleton() {
		
	}

	public synchronized static MongoClient getClient () {
		// TODO: get params from properties
		if (singletonClient == null) {
			String address = DbPropertiesSingleton.getMongoAddress();
			int port = DbPropertiesSingleton.getMongoPort();
			singletonClient = new MongoClient(address, port); // temp hardcode
		}
		return singletonClient;
	}
	
	/**
	 * There may be issues here if an instance of the client is held somewhere
	 * outside the singleton and it is closed from here
	 */
	public synchronized static void closeClient() {
		if (singletonClient != null) {
			singletonClient.close();
			singletonClient = null;
		}
	}
	
}
