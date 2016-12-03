package testUtilities;

import java.util.List;

public class TestBeanWithCollectionOfObject {
	List<TestSimpleBean> beans;

	public List<TestSimpleBean> getBeans() {
		return beans;
	}

	public void setBeans(List<TestSimpleBean> beans) {
		this.beans = beans;
	}
	
}
