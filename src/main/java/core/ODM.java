package core;

import java.util.List;

import dao.MongoDao;
import query.Selector;
import query.utils.QueryUtils;

/**
 * Facade for project
 * @author adam
 *
 */
public class ODM {
	public static void insert(Object dataObject) {
		MongoDao.insert(dataObject);
	}
	public static void insertMany(List<?> dataObjects) {
		MongoDao.insertMany(dataObjects);
	}
	public static void update(Object dataObject) {
		MongoDao.update(dataObject);
	}
	public static void updateAll(List<?> dataObjects) {
		MongoDao.updateAll(dataObjects);
	}
	public static void delete(Object dataObject) {
		MongoDao.delete(dataObject);
	}
	public static void deleteAll(List<?> dataObjects) {
		MongoDao.deleteAll(dataObjects);
	}
	public static <T, U> T[] castResult(U[] original, Class<? extends T[]> newType) {
		return QueryUtils.castResult(original, newType);
	}
	public static Selector selectorForClass(Class<?> returnClassType) {
		return new Selector(returnClassType);
	}
}
