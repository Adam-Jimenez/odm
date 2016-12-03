package mongoTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.client.MongoCollection;

import dao.exceptions.DbNotFound;
import dao.singleton.DbSingleton;

public class MongoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This is how we insert an array in mongo
	 */
	@Test
	public void testInsertObjectWithArray() throws DbNotFound {
		MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection("testCollection");
		List<String> names = Arrays.asList(new String[]{ "bob", "paul", "adam" });
		
		Document document = new Document();
		document.append("names", names);
		collection.insertOne(document);
	}

}
