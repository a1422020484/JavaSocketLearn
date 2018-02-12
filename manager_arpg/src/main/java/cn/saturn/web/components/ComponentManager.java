package cn.saturn.web.components;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ComponentManager implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		zyt.spring.component.ComponentManager.init(context, null);

		// 立马重载所有组件
		zyt.spring.component.ComponentManager.reloadAllComponent();
	}

}
