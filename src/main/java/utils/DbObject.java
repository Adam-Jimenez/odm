package utils;

import dao.MongoDao;

public abstract class DbObject {
	public void insert() {
		MongoDao.insert(this);
	}
	
	public void update() {
		MongoDao.update(this);
	}
	
	public void delete() {
		MongoDao.delete(this);
	}
}
