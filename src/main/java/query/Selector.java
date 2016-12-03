package query;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import dao.exceptions.DbNotFound;
import dao.singleton.DbSingleton;

public class Selector {

	private Instantiator instantiatior;
	private Class<?> classType;

	public Selector(Class<?> returnClassType) {
		super();
		this.classType = returnClassType;
		this.instantiatior = new Instantiator(returnClassType);
	}

	/**
	 * Get given class from db and create instance
	 * @return Newly create instance with data from db
	 */
	public Object get() {
		try {
			MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection(classType.getCanonicalName());
			Document document = collection.find().first();
			return instantiatior.instanciateFromDocument(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
