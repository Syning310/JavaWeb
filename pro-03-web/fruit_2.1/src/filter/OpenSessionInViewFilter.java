package filter;

import trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;


@WebFilter("*.do")
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       
        try {
            // 开启事务
            TransactionManager.beginTrans();
            System.out.println("开启事务...");
            
            // 放行
            filterChain.doFilter(servletRequest, servletResponse);
            
            // 如果一切顺利，则提交事务
            TransactionManager.commit();
            System.out.println("提交事务...");
            
        } catch (Exception e) {
            // 如果中途出现异常，则执行回滚
            try {
                System.out.println("回滚事务...");
                TransactionManager.rollback();
            } catch (SQLException throwables) {
                throw new RuntimeException(e);
            }
            
            throw new RuntimeException(e);
        }

        
        
    }

    @Override
    public void destroy() {

    }
}
