package cn.saturn.web.code;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import java.io.IOException;

/**
 * 只用于测试纯模式下的调用
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