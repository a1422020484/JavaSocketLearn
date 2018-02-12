package nettyServer.util.cache;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 持久化实体,与单个玩家数据绑定 <br>
 * 在实体内部数据变化后,需要调用{@link #update()},框架会在下一次保存数据时将已变化的数据进行保存.
 * <br>
 * 框架会把写数据库失败的数据以JSON格式写入到文件,使用的JSON库是fastJSON,
 * 所以,在实体类中,各字段是否需要序列化可使用fastJSON提供的注释来决定.
 * <br>
 * <br>
 * <b>实体类必须存在无参数构造器!</b>
 * 
 * @author yangxp
 */
public abstract class PersistEntity implements Cloneable {
	
	/*
	 * 持久化到数据库时的mod值
	 */
	volatile int sav;
	/*
	 * 修改次数,新创建或属性每变化一次增加1
	 */
	volatile int mod;
	
	/**
	 * 实体克隆源
	 */
	transient public PersistEntity cloneSource;

	// /*
	// * 标记此实体已删除? 
	// * 情况一: 对应数据库一条数据,这样的数据不需要删除
	// * 情况二: 对应数据库多条数据,那么应该由一个类(实体)来管理这些数据,记录下被删除的id,在更新时通过id来删除,能更早的释放内存
	// */
	// transient volatile boolean del;
	
	/**
	 * 实体持久化标记, 0失败(默认),1成功
	 * 不被克隆,复制出来的实体saveFlag为0
	 */
	@JSONField(deserialize = false, serialize = false)
	transient public volatile byte saveFlag;

	/**
	 * @return 玩家id
	 */
	@JSONField(deserialize = false, serialize = false)
	public abstract int getPlayerId();

	/**
	 * 在实体内部数据改变,需要更新到数据库时,调用此方法,并不会立即更新,将在下一次持久化时保存,下一次持久化的时间由保存策略而定.
	 */
	public void update() {
		mod++;
	}

	/**
	 * @return 内部数据是否改变过
	 */
	@JSONField(deserialize = false, serialize = false)
	public boolean isChanged() {
		return mod != sav;
	}
	
	/**
	 * 克隆实体用于保存
	 * <br>
	 * 克隆操作无法保证实体内部数据一致性,
	 * 虽然在克隆时通常只是读取数据进行复制,但在读的过程中有可能会因并发的修改产生异常,所以实体应自己保护内部数据.
	 * <br>
	 * <b>克隆过程中产生异常会导致数据丢失!!!</b>
	 * @return 克隆的实体
	 */
	protected abstract PersistEntity cloneEntity();
	
	/**
	 * 在克隆成功后调用,此时cloneSource已指向源
	 */
	protected void cloneAfter() {}

	/**
	 * @return 实体BEAN所代表的类
	 */
	protected Class<? extends PersistEntity> actualClass() {
		Class<? extends PersistEntity> thisClass = this.getClass();
		return thisClass;
	}
	
	
	// setter getter
	public int getSav() {
		return sav;
	}
	
	public void setSav(int sav) {
		this.sav = sav;
	}
	
	public int getMod() {
		return mod;
	}
	
	public void setMod(int mod) {
		this.mod = mod;
	}
}
