package filters;

import javax.servlet.*;
import java.io.IOException;

public class OpenSessionInViewFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        
        try {
            // 开启事务
            TransactionManager.beginTrans();

            // 放行
            filterChain.doFilter(servletRequest, servletResponse);

            // 若无异常情况则提交事务
            TransactionManager.commit();
        } catch(RuntimeException e) {
            // 如果发生了异常，则执行回滚
            try {
                TransactionManager.rollback();
            } catch (RuntimeException re) { 
                throw new RuntimeException(re);   
            }
            
            throw new RuntimeException(e);  // 这里抛出的是事务中异常的情况
        }

        
        
        
        
    }

    @Override
    public void destroy() {

    }
}
