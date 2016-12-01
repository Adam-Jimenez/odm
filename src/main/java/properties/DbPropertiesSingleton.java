package properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class DbPropertiesSingleton {

	private static final String dbConfigFilePath = "./dbConfig.cfg";
	private static final String MONGO_PORT_PROPERTY = "MONGO_PORT";
	private static final String MONGO_ADDRESS_PROPERTY = "MONGO_ADDRESS";
	private static final String MONGO_DEFAULT_DATABASE_PROPERTY = "MONGO_DEFAULT_DATABASE";
	private static Properties dbProperties;

	private DbPropertiesSingleton() {
		
	}
	
	public synchronized static Properties getDbProperties() {
		if (dbProperties == null) {
			dbProperties = new Properties();
			try {
				dbProperties.load(new FileInputStream(dbConfigFilePath));
			} catch (FileNotFoundException e) {
				// if config does not exist create and store default values
				setDefaultDbProperties();
				storeProperties();
			} catch (IOException e) {
				// if something is up with IO, run app with default values
				setDefaultDbProperties();
			}
		}
		return dbProperties;
	}
	
	public static int getMongoPort() {
		try {
			Integer port = Integer.parseInt(getDbProperties().getProperty(MONGO_PORT_PROPERTY));
			return port;
		} catch (Exception e) {
			System.err.println("Error fetching database port , fallback to default: " + getDefaultPort());
			return getDefaultPort();
		}
	}
	
	public static String getMongoAddress() {
		String address = getDbProperties().getProperty(MONGO_ADDRESS_PROPERTY);
		if (address == null) {
			return getDefaultAddress();
		} else {
			return address;
		}
	}
	
	public static String getDefaultDatabase() {
		String defaultDatabase = getDbProperties().getProperty(MONGO_DEFAULT_DATABASE_PROPERTY);
		if (defaultDatabase == null) {
			return getDefaultDatabase();
		} else {
			return defaultDatabase;
		}
	}
	
	private static void setDefaultDbProperties() {
		final String MONGO_PORT_DEFAULT_VALUE = String.valueOf(getDefaultPort());
		final String MONGO_ADDRESS_DEFAULT_VALUE = getDefaultAddress();
		final String MONGO_DEFAULT_DATABASE_DEFAULT_VALUE = getDefaultDb();

		dbProperties.setProperty(MONGO_PORT_PROPERTY, MONGO_PORT_DEFAULT_VALUE);
		dbProperties.setProperty(MONGO_ADDRESS_PROPERTY, MONGO_ADDRESS_DEFAULT_VALUE);
		dbProperties.setProperty(MONGO_DEFAULT_DATABASE_PROPERTY, MONGO_DEFAULT_DATABASE_DEFAULT_VALUE);
	}
	
	private static void storeProperties() {
		try {
			dbProperties.store(new FileOutputStream(dbConfigFilePath), "This is the database config used by odm");
		} catch (Exception e) {
			System.err.println("Could not store default properties");
		}
	}
	
	private static int getDefaultPort() {
		return 27017;
	}
	
	private static String getDefaultAddress() {
		return "localhost";
	}
	
	private static String getDefaultDb() {
		return "DEFAULT_DB";
	}
}
