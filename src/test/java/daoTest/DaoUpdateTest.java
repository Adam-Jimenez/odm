package daoTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dao.MongoDao;
import query.Selector;
import testUtilities.TestSimpleBean;

public class DaoUpdateTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Updating database document based on object reference
	 */
	@Test
	@Ignore
	public void testUpdate() {
		TestSimpleBean testBean = new TestSimpleBean();
		testBean.setAge(60); 
		MongoDao.insert(testBean);

		testBean.setAge(55);
		MongoDao.update(testBean);
	}
	
	@Test
	@Ignore
	public void testUpdateAfterSelect() {
		Selector selector = new Selector(TestSimpleBean.class);
		TestSimpleBean bean = (TestSimpleBean) selector.get();
		System.out.println(bean);
		bean.setAge(12);
		MongoDao.update(bean);
	}

}
