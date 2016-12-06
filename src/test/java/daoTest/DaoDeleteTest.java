package daoTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dao.MongoDao;
import testHelper.TestSimpleBean;

public class DaoDeleteTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void deleteTest() {
		TestSimpleBean testBean = new TestSimpleBean();
		testBean.setAge(123);

		MongoDao.insert(testBean);
		
		// add breakpoint here
		MongoDao.delete(testBean);
	}

}
