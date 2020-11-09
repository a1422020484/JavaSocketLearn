package nettyServer.dispatch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import nettyServer.dispatch.SessionManager;

/**
 * 控制器Annotation,用于类,方法<br>
 * 只有标注此Annotation的类被认为是控制器类.<br>
 * 控制器类里面标注此Annotation的方法识为一个Action.<br>
 * <br>
 * 如果要在控制器中拿到本次请求的Request实例,可在控制器中做如下声明<br>
 * <code>＠Resource Request req;</code> <br>
 * 控制器方法的返回值可以是Response,null或com.google.protobuf.MessageLite,<br>
 * <li>1.返回值是null时,不返回数据给客户端.</li> <li>
 * 2.返回值是MessageLite时,默认以方法执行成功处理,封装成Response对象返回给客户端.</li> <li>
 * 3.返回值是Response类型时,直接返回给客户端.</li> <br>
 * <strong>Response使用{@link xzj.core.dispatch.ResponseFactory}构造.</strong> <br>
 * <br>
 * 推送:<br>
 * <li>推送给指定玩家: {@link SessionManager#addMsg(int, xzj.core.dispatch.Response)}</li>
 * <li>推送给所有在线玩家: {@link SessionManager#addMsg(xzj.core.dispatch.Response)}</li>
 * <br>
 * <b>协议号: 101~199为框架保留号</b>
 * <br>
 * @author yangxp
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {

	/**
	 * 用来识别Action的标识,每个Action必须分配唯一的Id
	 *
	 * @return 业务Id
	 */
	int id() default -1;

//	/**
//	 * 当业务的执行时间超过{@link #timeout()}指定时间时,执行结果不返回.<br>
//	 * <p/>
//	 * <b>默认为0</b>
//	 *
//	 * @return 超时时间(毫秒), 0表示永不超时
//	 */
//	int timeout() default 0;
//
//	/**
//	 * 当一个Action的两次开始调用时间间隔小于{@link #interval()}指定时间时,
//	 * 返回错误提示"操作过于频繁,请稍后再试!",并且此Action不会执行,执行前过滤器会执行,执行后过滤器不会执行.<br>
//	 * <p/>
//	 * <b>默认为0</b>
//	 *
//	 * @return 操作时间间隔(毫秒), 0表示无间隔
//	 */
//	int interval() default 0;

	/**
	 * 如果为true,表示此次是登录操作,所有登录操作请求在单个线程中处理. <br>
	 * <p/>
	 * <b>忽略{@link Action#loginCheck()}</b>
	 * <b>忽略{@link Action#isCreatePlayer()}</b>
	 * <br>
	 * <b>默认为false</b>
	 * 
	 * @return 是否是登录操作
	 */
	boolean isLogon() default false;

	/**
	 * 如果需要登录验证,那么将检查{@link xzj.core.dispatch.Session#getPlayerId()}
	 * 是否为null,也就是说,{@link xzj.core.dispatch.Session#setPlayerId(Long)}后算登录成功.<br>
	 * 不需要登录验证有以下可能: <li>{@link #loginCheck()}为false</li> <li>{@link #isLogon()}
	 * 为true</li> <li>{@link #user()}等于{@link User#System}</li> <br>
	 * 以上三种操作分别在单线程执行
	 * 
	 * <br>
	 * <b>默认为true</b>
	 *
	 * @return 是否需要登录验证
	 */
	boolean loginCheck() default true;
	
	/**
	 * 如果为true,表示此次是创建角色操作,所有创建角色操作请求在单个线程中处理.
	 * <br>
	 * <b>默认为false</b>
	 * @return 是否是创建角色
	 */
	boolean isCreatePlayer() default false;
	
	/**
	 * 标识请求的数据是否是加密的,如果是加密的,那么先解密,再解析
	 * <br>
	 * <b>默认为true</b>
	 * @return
	 */
	boolean isEncrypted() default true;

	/**
	 * 指定Action的使用用户对象<br>
	 * <li>{@link Action.User#Player}: 使用对象是玩家.<b>默认</b></li> <li>
	 * {@link Action.User#System}: 使用对象是管理员,需要验证SIGN.</li> <br>
	 * <br>
	 * <b>声明为{@link Action.User#System}的所有Action由单线程执行.</b>
	 * 
	 * @return 用户对象
	 */
	User user() default User.Player;

	public enum User {
		/**
		 * 玩家权限,声明此方法提供给普通玩家使用</br>
		 */
		Player,
		/**
		 * 管理员权限,接收的数据使用RSA加密算法加密解密,返回的数据不加密.
		 * </br>
		 * <b>忽略{@link Action#loginCheck()}自设定,固定为false.</b>
		 * </br>
		 * <b>忽略{@link Action#isEncrypted()}自设定,固定为false.</b>
		 */
		System;
	}
}
