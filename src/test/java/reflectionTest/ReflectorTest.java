package reflectionTest;

import static org.junit.Assert.*;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.client.MongoCollection;

import TestUtilities.TestBean;
import dao.exceptions.DbNotFound;
import dao.singleton.DbSingleton;
import reflection.Reflector;

public class ReflectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This test shows inserting an object directly in a document fails
	 * 
	 * @throws DbNotFound
	 */
	@Test
	@Ignore
	public void test() throws DbNotFound {
		MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection("testCollection");

		TestBean testBean = new TestBean();
		testBean.setAge(1);

		/*
		 * The reason why we do two insertOnes is because the second insert
		 * fails therefore we couldn't see the first one working
		 */

		// this works
		Document document = new Document("test", "test");
		// this does not
		Document document2 = new Document("testBean", testBean);

		collection.insertOne(document);
		collection.insertOne(document2);

		// (the database should only contain test after this)

		// We need a better way to insert Objects in a noSQL database (see next
		// test)
	}

	/**
	 * This test shows that using reflection can solve the problem of storing an
	 * object
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReflection() throws Exception {
		MongoCollection<Document> collection = DbSingleton.getDefaultDB().getCollection("testCollection");

		TestBean testBean = new TestBean();
		testBean.setAge(1);

		Document document = Reflector.documentFromObject(testBean);
		collection.insertOne(document);

	}

}
