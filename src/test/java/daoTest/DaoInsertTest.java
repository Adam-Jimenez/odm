package daoTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dao.MongoDao;
import testHelper.TestBeanWithArray;
import testHelper.TestBeanWithArrayOfObject;
import testHelper.TestBeanWithCollectionOfObject;
import testHelper.TestBeanWithCollectionOfPrimitive;
import testHelper.TestBeanWithEverything;
import testHelper.TestBeanWithMapOfObject;
import testHelper.TestBeanWithMapOfPrimitive;
import testHelper.TestBeanWithObjectField;
import testHelper.TestInheritanceBean;
import testHelper.TestSimpleBean;

public class DaoInsertTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Storing simple bean
	 */
	@Test
	@Ignore
	public void simpleBeanTest() {
		TestSimpleBean testBean = new TestSimpleBean();
		testBean.setAge(10);

		MongoDao.insert(testBean);
	}

	/**
	 * Storing bean with sub object field to test recursive calling
	 */
	@Test
	@Ignore
	public void beanWithSubObjectTest() {
		TestBeanWithObjectField testBean = new TestBeanWithObjectField();
		testBean.setName("Paul");

		TestSimpleBean testSimpleBean = new TestSimpleBean();
		testSimpleBean.setAge(25);

		testBean.setObjectField(testSimpleBean);

		MongoDao.insert(testBean);
	}

	/**
	 * Storing bean with an array of primitives (Integer) NOTE LIMITATIONS:
	 * Integer is used here, because int fails
	 */
	@Test
	@Ignore
	public void beanWithArrayTest() {
		TestBeanWithArray testBean = new TestBeanWithArray();
		Integer[] numbers = { 1, 2, 3, 4 };
		testBean.setNumbers(numbers);

		MongoDao.insert(testBean);
	}

	/**
	 * Storing bean with an array of objects (SimpleBean)
	 */
	@Test
	@Ignore
	public void beanWithArrayOfObjectTest() {
		TestBeanWithArrayOfObject testBean = new TestBeanWithArrayOfObject();
		TestSimpleBean[] beans = new TestSimpleBean[3];
		(beans[0] = new TestSimpleBean()).setAge(2);
		(beans[1] = new TestSimpleBean()).setAge(4);
		(beans[2] = new TestSimpleBean()).setAge(6);
		testBean.setBeans(beans);

		MongoDao.insert(testBean);
	}

	/**
	 * Storing a bean that inherits from another
	 */
	@Test
	@Ignore
	public void beanWithInheritanceTest() {
		TestInheritanceBean testBean = new TestInheritanceBean();
		testBean.setAge(50);
		testBean.setBool(true);
		testBean.setByt((byte) 127);
		testBean.setC('k');
		testBean.setD(4.20);
		testBean.setF(3.14f);
		testBean.setL(100000000000l);
		testBean.setS((short) 30000);

		MongoDao.insert(testBean);
	}

	/**
	 * Storing a bean with a collection of primitives types as field
	 */
	@Test
	@Ignore
	public void beanWithCollectionOfPrimitiveTest() {
		TestBeanWithCollectionOfPrimitive testBean = new TestBeanWithCollectionOfPrimitive();
		List<Integer> listOfNumbers = new ArrayList<Integer>();
		listOfNumbers.add(1);
		listOfNumbers.add(2);
		listOfNumbers.add(3);

		testBean.setListOfNumbers(listOfNumbers);
		MongoDao.insert(testBean);
	}

	/**
	 * Storing a bean with a collection of objects as field
	 */
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
	}

	/**
	 * Storing a bean with a map of primitives as field
	 */
	@Test
	@Ignore
	public void beanWithMapOfPrimitivesTest() {
		TestBeanWithMapOfPrimitive testBean = new TestBeanWithMapOfPrimitive();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("value1", 1);
		map.put("value10", 10);
		map.put("value100", 100);
		testBean.setValues(map);
		MongoDao.insert(testBean);
	}

	/**
	 * Storing a bean with a map of object as field
	 */
	@Test
	@Ignore
	public void beanWithMapOfObjectsTest() {
		TestBeanWithMapOfObject testBean = new TestBeanWithMapOfObject();
		Map<Integer, TestSimpleBean> map = new TreeMap<Integer, TestSimpleBean>();
		TestSimpleBean bean1 = new TestSimpleBean();
		TestSimpleBean bean2 = new TestSimpleBean();
		TestSimpleBean bean3 = new TestSimpleBean();

		bean1.setAge(4);
		bean2.setAge(5);
		bean3.setAge(6);

		map.put(1, bean1);
		map.put(2, bean2);
		map.put(3, bean3);

		testBean.setValues(map);

		MongoDao.insert(testBean);
	}

	/**
	 * Now storing a bean that uses all previous concepts
	 */
	@Test
	@Ignore
	public void beanWithEverythingTest() {
		TestBeanWithEverything testBean = new TestBeanWithEverything();
		Long alonglong = 1234567890987654321l;
		TestInheritanceBean types = new TestInheritanceBean();
		types.setAge(96);
		types.setBool(false);
		types.setByt((byte) 8);
		types.setC('!');
		types.setD(0.1);
		types.setF(123.42f);
		types.setL(1234l);
		types.setS((short) 1234);

		List<TestSimpleBean> beans = new ArrayList<TestSimpleBean>();
		for (int i = 0; i < 10; i++) {
			beans.add(new TestSimpleBean());
		}

		/*
		 * This doesn't work because it doesn't recognize the map used as
		 * generic as a class
		 */
		/*
		  Map<String, Map<String, TestSimpleBean>> superMap = new
		  HashMap<String, Map<String, TestSimpleBean>>(); for(int i = 0; i <
		  10; i++) { Map<String, TestSimpleBean> miniMap = new HashMap<String,
		  TestSimpleBean>(); miniMap.put("hey", new TestSimpleBean());
		  miniMap.put("now", new TestSimpleBean());
		  superMap.put(String.valueOf(i), miniMap); }
		*/
		testBean.setLooooooooooooooooooooooooooooooooooooong(alonglong);
		testBean.setDoubledouble(12345.678901);
		testBean.setTypes(types);
		testBean.setBeans(beans);
		Character[][] arr = new Character[2][2];
		arr[0][0] = 'a';
		arr[0][1] = 'b';
		arr[1][0] = 'c';
		arr[1][1] = 'd';
		testBean.setMultiDimensionalArray(arr);
		//testBeanI.setSuperMap(superMap);

		MongoDao.insert(testBean);
	}
}
