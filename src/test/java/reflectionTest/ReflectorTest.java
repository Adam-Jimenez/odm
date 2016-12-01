package reflectionTest;

import static org.junit.Assert.*;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import TestUtilities.TestBean;
import reflection.Reflector;

public class ReflectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		TestBean testBean = new TestBean();
		Document doc = Reflector.documentFromObject(testBean);
	}
	
}
