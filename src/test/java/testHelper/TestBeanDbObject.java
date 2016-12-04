package testHelper;

import utils.DbObject;

public class TestBeanDbObject extends DbObject {
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
		update();
	}

	@Override
	public String toString() {
		return "age: " + age;
	}
}
