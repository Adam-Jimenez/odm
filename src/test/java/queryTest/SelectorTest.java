package queryTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import testUtilities.*;

import query.Selector;
import query.utils.QueryUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dao.MongoDao;

public class SelectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void getTest() {
		TestSimpleBean testBean = new TestSimpleBean();
		testBean.setAge(99);
		MongoDao.insert(testBean);
		Selector simpleBeanSelector = new Selector(TestSimpleBean.class);
		TestSimpleBean result = (TestSimpleBean) simpleBeanSelector.get();
		assertTrue(testBean.getAge() == result.getAge());
	}
	
	@Test
	public void getAllTest() {
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
		Selector selector = new Selector(TestSimpleBean.class);
		TestSimpleBean[] beanies = QueryUtils.castResult(selector.getAll(), TestSimpleBean[].class);
		System.out.println(Arrays.toString(beanies));
	}
	
	@Test

}
