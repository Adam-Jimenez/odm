package daoTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.client.model.Filters;

import core.ODM;
import query.Selector;
import testHelper.TestSimpleBean;

public class ODMTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void test() {
		Selector selector = ODM.selectorForClass(TestSimpleBean.class);
		TestSimpleBean bean = (TestSimpleBean) selector.get(Filters.eq("id", "0"));
	}

}
