package daoTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import TestUtilities.TestBean;
import dao.MongoDao;

public class DaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test(){
		TestBean testBean = new TestBean();
		testBean.setAge(1);

		MongoDao.store(testBean);
	}
	
}
