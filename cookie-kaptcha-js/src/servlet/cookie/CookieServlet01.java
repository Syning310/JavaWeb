package servlet.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cookie01")
public class CookieServlet01 extends HttpServlet {


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        // 1、创建一个 Cookie 对象
        Cookie cookie = new Cookie("name", "宁");
        
        // 2、将这个 Cookie 对象，保存到 客户端
        response.addCookie(cookie);

        System.out.println("你好世界");
        
        request.getRequestDispatcher("hello.html").forward(request, response);
        
    }
}
