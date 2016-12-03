package testUtilities;

import java.io.Serializable;
import java.util.Map;

/*
 *  kind of everything but not really
 */
public class TestBeanWithEverything extends TestBeanWithCollectionOfObject implements Serializable {

	private static final long serialVersionUID = 1828829564701651431L;
	private final Character[][] finalMultiDimensionalArray = { { 'h', 'a' }, { 'e', 'd' }, { 'l', 'a' }, { 'l', 'm' },
			{ 'o', ' ' } };
	private Character[][] multiDimensionalArray;
	private TestInheritanceBean types;
	private Map<String, Map<String, TestSimpleBean>> superMap;
	private Long looooooooooooooooooooooooooooooooooooong;
	private double doubledouble;

	public TestBeanWithEverything() {
		super();
		multiDimensionalArray = null;
	}

	public TestInheritanceBean getTypes() {
		return types;
	}

	public void setTypes(TestInheritanceBean types) {
		this.types = types;
	}

	public Map<String, Map<String, TestSimpleBean>> getSuperMap() {
		return superMap;
	}

	public void setSuperMap(Map<String, Map<String, TestSimpleBean>> superMap) {
		this.superMap = superMap;
	}

	public Long getLooooooooooooooooooooooooooooooooooooong() {
		return looooooooooooooooooooooooooooooooooooong;
	}

	public void setLooooooooooooooooooooooooooooooooooooong(Long looooooooooooooooooooooooooooooooooooong) {
		this.looooooooooooooooooooooooooooooooooooong = looooooooooooooooooooooooooooooooooooong;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Character[][] getMultiDimensionalArray() {
		return multiDimensionalArray;
	}

	public void setMultiDimensionalArray(Character[][] multiDimensionalArray) {
		this.multiDimensionalArray = multiDimensionalArray;
	}

	public double getDoubledouble() {
		return doubledouble;
	}

	public void setDoubledouble(double doubledouble) {
		this.doubledouble = doubledouble;
	}

	public Character[][] getFinalMultiDimensionalArray() {
		return finalMultiDimensionalArray;
	}

}
