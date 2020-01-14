package mongodb.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import mongodb.MongoDBMgr;

public class SpringMongoApp {

	private static final String TABLE_NAME = "user";
	private static final String COLLECTION_PERSON = "person";
	private static final Log log = LogFactory.getLog(SpringMongoApp.class);

	public static void main(String[] args) throws Exception {

		MongoOperations mongoOps = new MongoTemplate(MongoDBMgr.mongoClient, TABLE_NAME);
		mongoOps.insert(new Person("Joe", 34));

		log.info(mongoOps.findOne(new Query(Criteria.where("name").is("Joe")), Person.class));

//		mongoOps.dropCollection("person");
	}
}
