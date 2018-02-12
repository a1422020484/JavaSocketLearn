package cn.saturn.web.code;

import net.paoding.rose.RoseFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class RoseFilterT extends RoseFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        super.doFilter(request, response, filterChain);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
