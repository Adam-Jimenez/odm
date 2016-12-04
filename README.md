# ODM

ODM, a lightweight and unintrusive Object-Document Mapper.

See: [Usage Example](https://github.com/Adam-Jimenez/odm-usage-example)

## Installation
1. Have MongoDb installed and running
2. Create or modify the dbConfig.cfg file in the root of the project to configure the connection to MongoDb. It must contain the following:

```
#dbConfig.cfg
MONGO_ADDRESS=localhost
MONGO_DEFAULT_DATABASE=DEFAULT_DB
MONGO_PORT=27017
```

## Usage

```
Selector selector = ODM.selectorForClass(TestSimpleBean.class);
TestSimpleBean bean = (TestSimpleBean) selector.get(Filters.eq("id", "0"));
```

Fetches a TestSimpleBean from the db where the id field is equal to 0
Note: You can pass any filters from the MongoDb driver library
see: [documentation]( http://mongodb.github.io/mongo-java-driver/3.0/builders/filters/)

```
ODM.insert(Object object);
```

Inserts an object in the database specified in configuration in a collection of the name of the class inserted

```
ODM.update(Object object);
```

Updates an object previously inserted or fetched

```
ODM.delete(Object object);
```

Deletes an object previously inserted or fetched

You can also extend your object from DbObject class. 
It will enable you to call: 

```
object.insert();
object.update();
object.delete();
```

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

This project started as a school project.

## Credits

Adam Jimenez
