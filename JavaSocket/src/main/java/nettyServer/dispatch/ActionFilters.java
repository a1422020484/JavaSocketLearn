package nettyServer.dispatch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yangxp
 *
 */
@Component
public class ActionFilters implements ApplicationContextAware {
	
	static List<ActionFilter> filters;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		filters = new ArrayList<ActionFilter>(ctx.getBeansOfType(ActionFilter.class).values());
	}

}
