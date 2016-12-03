package daoTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dao.MongoDao;
import testUtilities.TestBeanWithArray;
import testUtilities.TestBeanWithArrayOfObject;
import testUtilities.TestBeanWithObjectField;
import testUtilities.TestInheritanceBean;
import testUtilities.TestSimpleBean;

public class DaoTest {

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
	public void simpleBeanTest(){
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
	 * Storing bean with an array of primitives (Integer)
	 * NOTE LIMITATIONS: Integer is used here, because int fails
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
	
	@Test
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
}
