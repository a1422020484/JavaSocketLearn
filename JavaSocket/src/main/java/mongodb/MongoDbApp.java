package mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.Test;

public class MongoDbApp {

	private static final String TABLE_NAME = "user";
	private static final String COLLECTION_AGGRE = "aggre";
	private static final String COLLECTION_USER = "user";

	@Test
	public void insertOne() {
		User user = new User();
		user.setAddress("guandong");
		user.setAge(90);
		user.setName("fafa");
		MongoDBMgr.insertOne(TABLE_NAME, COLLECTION_USER, user.toDoucment());
	}

	@Test
	public void insertMany() {
		List<Document> list = new ArrayList<Document>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setAddress("guandong" + i);
			user.setAge(90 + i);
			user.setName("fafa" + i);
			list.add(user.toDoucment());
		}
		MongoDBMgr.insertMany(TABLE_NAME, COLLECTION_USER, list);
	}

	@Test
	public void findMany() {
		MongoDBMgr.getCollection(TABLE_NAME, COLLECTION_USER);
		Document filterDoc = new Document();
		filterDoc.append("name", "fafa");
		List<Document> findInterable = MongoDBMgr.findMany(TABLE_NAME, COLLECTION_USER, filterDoc);
		List<User> users = new ArrayList<User>();
		findInterable.stream().forEach(e -> {
			User tempUser = new User();
			tempUser.toModel(e);
			users.add(tempUser);
		});
		users.stream().forEach(System.out::println);
	}

	@Test
	public void find() {
		MongoDBMgr.getCollection(TABLE_NAME, COLLECTION_USER);
		List<Document> findInterable = MongoDBMgr.findAll(TABLE_NAME, COLLECTION_USER);
		List<User> users = new ArrayList<User>();
		findInterable.stream().forEach(e -> {
			User tempUser = new User();
			tempUser.toModel(e);
			users.add(tempUser);
		});
		users.stream().forEach(System.out::println);
	}
}
