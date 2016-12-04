package testHelper;

import java.util.Map;

public class TestBeanWithMapOfObject {
	private Map<Integer, TestSimpleBean> values;

	public Map<Integer, TestSimpleBean> getValues() {
		return values;
	}

	public void setValues(Map<Integer, TestSimpleBean> values) {
		this.values = values;
	}
	
}
