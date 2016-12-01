package dao.singleton;

import com.mongodb.MongoClient;

public final class ClientSingleton {
	
	private static MongoClient singletonClient;

	private ClientSingleton() {
		
	}

	public synchronized static MongoClient getClient () {
		// TODO: get params from properties
		if (singletonClient == null) {
			singletonClient = new MongoClient("localhost", 27017); // temp hardcode
		}
		return singletonClient;
	}
	
	public synchronized static void closeClient() {
		if (singletonClient != null) {
			singletonClient.close();
			singletonClient = null;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		closeClient();
	}
}
