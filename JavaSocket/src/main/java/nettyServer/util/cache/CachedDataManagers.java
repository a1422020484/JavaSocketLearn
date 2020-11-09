package nettyServer.util.cache;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 缓存管理器管理类
 * 
 * @author yangxp
 */
@Component
public class CachedDataManagers implements ApplicationContextAware {
	/**
	 * 排好序的管理类列表,排序规则:<br>
	 * <li>1.按priority从小到大排序</li>
	 * <li>2.按管理类类名以自然顺序排序</li>
	 */
	@SuppressWarnings("rawtypes")
	static List<CachedDataManager> sortedList;
	static Map<Class<?>, CachedDataManager<?>> managersMap;

	@SuppressWarnings("rawtypes")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		List<CachedDataManager>sortedList = new ArrayList<>(applicationContext.getBeansOfType(CachedDataManager.class).values());
		Map<Class<?>, CachedDataManager<?>> managersMap = new HashMap<>(256);
		for (CachedDataManager<?> cdm : sortedList) {
			// 检查实体类是否有无参数构造器
			try {
				cdm.getParameterizedType().getConstructor(new Class[0]);
			} catch (NoSuchMethodException e) {
				throw new Error("实体类必须有无参数构造器: " + cdm.getParameterizedType().getName());
			} catch (SecurityException e) {
			}
			managersMap.put(cdm.getParameterizedType(), cdm);
		}
		Collections.sort(sortedList, (o1, o2) -> {
            int c = o1.getPriority() - o2.getPriority();
            if (c != 0) {
                return c;
            }
            // 比较类名
            String n1 = o1.getClass().getSimpleName();
            String n2 = o2.getClass().getSimpleName();
            return n1.compareTo(n2);
        });
		CachedDataManagers.sortedList = Collections.unmodifiableList(sortedList);
		CachedDataManagers.managersMap = Collections.unmodifiableMap(managersMap);
	}
}
