package utilsTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.singleton.CachedObjects;
import testHelper.TestBeanDbObject;

public class DbObjectTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDbObject() {
		TestBeanDbObject bean = new TestBeanDbObject();
		bean.insert();

		//break point here
		bean.setAge(58);
		// and here
		bean.setAge(47);
		
		bean.delete();
		
		assertFalse(CachedObjects.hasReference(bean));
		bean = null;
	}

}
