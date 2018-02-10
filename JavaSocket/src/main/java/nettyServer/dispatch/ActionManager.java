package nettyServer.dispatch;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import nettyServer.dispatch.annotation.Action;
import nettyServer.util.LogKey;

/**
 * Action管理器,负责加载Action,ActionFilter
 *
 * @author zuojie.x
 */
public class ActionManager implements ApplicationContextAware {
	private final Logger log = LoggerFactory.getLogger(LogKey.CORE);
	private ApplicationContext context;
	
	private static Map<Integer, ActionInvoker> actionIdMap = new HashMap<>(256);

	private void load() {
		Map<String, Object> objects = context.getBeansWithAnnotation(Action.class);
		for (Map.Entry<String, Object> entry : objects.entrySet()) {
			Object actionObj = entry.getValue();
			for (Method method : actionObj.getClass().getDeclaredMethods()) {
				if (method.getAnnotation(Action.class) == null)
					continue;
				// 检查含有注解Action.class的方法的Modifier,不能是私有的或静态的
				if (Modifier.isStatic(method.getModifiers()))
					throw new Error("方法有@Action注解,不能声明为static > " + actionObj.getClass().getSimpleName() + "." + method.getName() + "(...)");
				if (Modifier.isPrivate(method.getModifiers()))
					throw new Error("方法有@Action注解,不能声明为private > " + actionObj.getClass().getSimpleName() + "." + method.getName() + "(...)");

				ActionInvoker actionInvoker = new ActionInvoker(actionObj, method);
				int actionId = actionInvoker.getActionId();
				if (actionId == -1)
					throw new Error("未设置Action id, action:" + actionInvoker.getActionDesc());
				if (actionIdMap.containsKey(actionId))
					throw new Error("Action id 重复:" + actionId);
				actionIdMap.put(actionId, actionInvoker);
				log.debug("load Action -> {}", actionInvoker.getActionDesc());
			}
		}
		log.debug("total Action count -> {}.", actionIdMap.size());
	}

	/**
	 * Action执行器
	 *
	 * @param request
	 * @return ActionInvoker
	 */
	static ActionInvoker getActionInvoker(Request request) {
		return actionIdMap.get(request.getActionId());
	}

	/**
	 * Action执行器
	 *
	 * @param actionId
	 * @return ActionInvoker
	 */
	public static ActionInvoker getActionInvoker(int actionId) {
		return actionIdMap.get(actionId);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
		load();
	}
}
