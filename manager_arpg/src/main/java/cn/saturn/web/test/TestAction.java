package cn.saturn.web.test;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class TestAction implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {

//		// 服务器列表测试
//		List<ServerSection> sectionList = LoginAction.getServerSections("Saturn");
//		List<ServerItem> serverList = LoginAction.getServerItems(1, sectionList);
//		System.out.println(serverList);

	}

}
