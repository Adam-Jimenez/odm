package queryTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

import query.Selector;
import query.utils.QueryUtils;
import testHelper.*;

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
	@Ignore
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
	@Ignore
	public void getBeanWithObjectFieldTest() {
		TestBeanWithObjectField testBean = new TestBeanWithObjectField();
		testBean.setName("Paul");

		TestSimpleBean testSimpleBean = new TestSimpleBean();
		testSimpleBean.setAge(25);

		testBean.setObjectField(testSimpleBean);

		MongoDao.insert(testBean);
		Selector selector = new Selector(TestBeanWithObjectField.class);
		TestBeanWithObjectField result = (TestBeanWithObjectField)selector.get(eq("name", "Paul"));
		assertTrue(result.getObjectField().getAge() == 25);
	}
	
	@Test
	@Ignore
	public void getBeanWithCollectionOfPrimitiveFieldTest() {
		TestBeanWithCollectionOfPrimitive testBean = new TestBeanWithCollectionOfPrimitive();
		List<Integer> listOfNumbers = new ArrayList<Integer>();
		listOfNumbers.add(1);
		listOfNumbers.add(2);
		listOfNumbers.add(3);

		testBean.setListOfNumbers(listOfNumbers);
		MongoDao.insert(testBean);
		
		Selector selector = new Selector(TestBeanWithCollectionOfPrimitive.class);
		TestBeanWithCollectionOfPrimitive result = (TestBeanWithCollectionOfPrimitive) selector.get();
		for(int number: result.getListOfNumbers()) {
			System.out.println(number);
		}
	}
	
	@Test
	@Ignore
	public void beanWithCollectionOfObjectTest() {
		TestBeanWithCollectionOfObject testBean = new TestBeanWithCollectionOfObject();
		List<TestSimpleBean> beans = new ArrayList<TestSimpleBean>();

		TestSimpleBean bean1 = new TestSimpleBean();
		TestSimpleBean bean2 = new TestSimpleBean();
		TestSimpleBean bean3 = new TestSimpleBean();

		bean1.setAge(4);
		bean2.setAge(5);
		bean3.setAge(6);

		beans.add(bean1);
		beans.add(bean2);
		beans.add(bean3);

		testBean.setBeans(beans);
		MongoDao.insert(testBean);

		Selector selector = new Selector(TestBeanWithCollectionOfObject.class);
		TestBeanWithCollectionOfObject result = (TestBeanWithCollectionOfObject)selector.get();
		System.out.println(Arrays.toString(result.getBeans().toArray()));
	}
	
}
