package top.chorg.iCommerce.response.debug;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

public class MasterResponse extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        // 设置响应内容类型
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        // 实际的逻辑是在这里
        PrintWriter out = response.getWriter();
        out.println("所有操作已完成");
    }
}
