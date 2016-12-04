package testHelper;

public class TestBeanWithObjectField {
	private int id;
	private String name;
	private TestSimpleBean objectField;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TestSimpleBean getObjectField() {
		return objectField;
	}
	public void setObjectField(TestSimpleBean objectField) {
		this.objectField = objectField;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "id: "+id+" name: "+name+" objectField: "+objectField.toString();
	}

}
