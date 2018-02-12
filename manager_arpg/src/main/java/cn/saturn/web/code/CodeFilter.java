package cn.saturn.web.code;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 只用于测试纯模式下的调用
 *
 */
public class CodeFilter implements Filter {
	protected ApplicationContext context;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// HttpServletRequest request = (HttpServletRequest) req;
		// System.out.println("url=" + request.getRequestURI());
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
}