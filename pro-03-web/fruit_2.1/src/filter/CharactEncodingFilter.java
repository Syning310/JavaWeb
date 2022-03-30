package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;


import java.io.IOException;


@WebFilter("*.do")
public class CharactEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 设置编码；HttpServletRequest接口继承于ServletRequest接口
        servletRequest.setCharacterEncoding("UTF-8");
        
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
        
    }

    @Override
    public void destroy() {

    }
}
