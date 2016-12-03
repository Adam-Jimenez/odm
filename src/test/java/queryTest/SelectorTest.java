package queryTest;

import static org.junit.Assert.*;
import testUtilities.*;
import query.Selector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SelectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void instantiationTest() {
		Selector simpleBeanSelector = new Selector(TestSimpleBean.class);
		TestSimpleBean testBean = (TestSimpleBean) simpleBeanSelector.get();
		assertTrue(testBean != null);
		System.out.println(testBean.getAge());
	}

}
