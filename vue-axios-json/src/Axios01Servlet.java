import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/axios01.do")
public class Axios01Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String uname = req.getParameter("uname");
        String pwd = req.getParameter("pwd");


        System.out.println("uname = " + uname);
        System.out.println("pwd = " + pwd);
        
        
        uname = "月";
        pwd = "0216";
        
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(uname + " " +pwd);
        
    }
}



