package filters;

import javax.servlet.*;

import java.io.IOException;




public class Demo01Filter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        System.out.println("hello doFilter01");
        
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("hello doFilete  r02");
        
    }

    @Override
    public void destroy() {

    }
}
