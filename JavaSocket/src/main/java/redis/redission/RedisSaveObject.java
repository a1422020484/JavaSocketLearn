package redis.redission;

import org.junit.Test;
import org.redisson.api.RBucket;

import redis.redission.userpojo.User;

/**
 * @author yangxp
 * 2020年7月18日上午10:52:08
 * <p>描述：redis 存储对象的方法
 */
public class RedisSaveObject {
	
	public void jsonTest() {
		User user = new User();
		user.setId(1);
		user.setName("ttt");
		user.setAddress("fff");
		RedisObjectJson.setJsonString(String.valueOf(user.getId()), user);
		
		User cc =(User) RedisObjectJson.getJsonObject("2", new User());
		System.out.println(cc.toString());
		RedisObjectJson.redissionClient.shutdown();
	}
	
	@Test
	public void serializeTest() {
		User user = new User();
		user.setId(2);
		user.setName("ydf");
		user.setName("user");
		byte[] userBytes = RedisObjectSerialize.serizlize(user);
		RBucket<Object> keyObject = RedisObjectSerialize.redissionClient.getBucket(String.valueOf(user.getId()));
		keyObject.set(userBytes);
		
		RBucket<Object> keyObjectRet = RedisObjectSerialize.redissionClient.getBucket(String.valueOf(user.getId()));
		byte[] userByteObj = (byte[])keyObjectRet.get();
		System.out.println(RedisObjectSerialize.deserialize(userByteObj).toString());
		
		RedisObjectSerialize.redissionClient.shutdown();
	}
}
