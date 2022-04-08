package filters;


import com.sun.deploy.net.HttpRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// 只允许请求 index 页面和 login 页面


//@WebFilter(urlPatterns = {"*.do", "*.html"},
//            initParams = {
//                @WebInitParam(name = "bai",
//                        value = "/book_store/page.do?operate=page&page=user/login,/book_store/user.do?null")
//            })


public class SessionFilter implements Filter {

    List<String> baiList;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String bai = filterConfig.getInitParameter("bai");
        String[] baiArr = bai.split(",");
        baiList = Arrays.asList(baiArr); 
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        // http://localhost:8080/book_store/page.do?operate=page&page=user/login
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getQueryString() = " + request.getQueryString());
        
        
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String str = uri + "?" + queryString;
        
        if (baiList.contains(str)) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession() ;
            Object currUserObj = session.getAttribute("currUser");

            if(currUserObj==null){
                response.sendRedirect("page.do?operate=page&page=user/login");
            }else{
                filterChain.doFilter(request,response);
            }
        }
 
    }

    @Override
    public void destroy() {

    }
}
