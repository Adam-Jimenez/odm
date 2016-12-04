package daoTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dao.MongoDao;
import dao.singleton.CachedObjects;
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
	
	@Test
	public void updateAllTest() {
		List<TestSimpleBean> testBeanList = new ArrayList<TestSimpleBean>();
		TestSimpleBean testBean1 = new TestSimpleBean();
		TestSimpleBean testBean2 = new TestSimpleBean();
		TestSimpleBean testBean3 = new TestSimpleBean();
		testBean1.setAge(31);
		testBean2.setAge(32);
		testBean3.setAge(33);
		testBeanList.add(testBean1);
		testBeanList.add(testBean2);
		testBeanList.add(testBean3);
		
		MongoDao.insertMany(testBeanList);		
		
		testBean1.setAge(11);
		testBean2.setAge(22);
		testBean3.setAge(33);
		
		MongoDao.updateAll(testBeanList);
	}

}
