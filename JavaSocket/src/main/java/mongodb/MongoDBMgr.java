package mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBMgr {

	private static final String HOST = "192.168.96.141";
	private static final int PORT = 27017;
	public static final MongoClient mongoClient;

	static {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).minConnectionsPerHost(2).build();
		ServerAddress serverAddress = new ServerAddress(HOST, PORT);
		mongoClient = new MongoClient(serverAddress, mongoClientOptions);
	}

	public static void insertOne(String tableName, String collectionName, Document doc) {
		getCollection(tableName, collectionName).insertOne(doc);
	}

	public static void insertMany(String tableName, String collectionName, List<Document> docs) {
		getCollection(tableName, collectionName).insertMany(docs);
	}

	public static List<Document> findAll(String tableName, String collectionName) {
		List<Document> modelList = new ArrayList<Document>();
		FindIterable<Document> allResult = getCollection(tableName, collectionName).find();
		allResult.forEach(new Block<Document>() {

			@Override
			public void apply(Document t) {
				modelList.add(t);
			}
		});
		return modelList;
	}

	public static List<Document> findMany(String tableName, String collectionName, Document filter) {
		List<Document> modelList = new ArrayList<Document>();
		FindIterable<Document> allResult = getCollection(tableName, collectionName).find(filter);
		allResult.forEach(new Block<Document>() {

			@Override
			public void apply(Document t) {
				modelList.add(t);
			}
		});
		return modelList;
	}

	public static MongoCollection<Document> getCollection(String tableName, String collectionName) {
		MongoDatabase mongoDatabase = mongoClient.getDatabase(tableName);
		return mongoDatabase.getCollection(collectionName);
	}

}
