package daoTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.MongoDao;
import testUtilities.TestSimpleBean;

public class DaoUpdateTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdate() {
		TestSimpleBean testBean = new TestSimpleBean();
		testBean.setAge(60); 
		MongoDao.insert(testBean);

		testBean.setAge(55);
		MongoDao.update(testBean);
	}

}
