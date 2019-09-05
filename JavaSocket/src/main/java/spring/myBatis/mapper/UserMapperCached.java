package spring.myBatis.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cache.decorators.FifoCache;

import spring.po.User;

@CacheNamespace(eviction = FifoCache.class, flushInterval = 60000, size = 512, readWrite = true)
public interface UserMapperCached {
	@Select("select name from user t where t.id = #{id} ")
	@Options(useCache = true)
	String getName(int id);

	@Select("select * from user t where t.id = #{id} ")
	@Options(useCache = true)
	User queryUserCachedById(int id);
}
