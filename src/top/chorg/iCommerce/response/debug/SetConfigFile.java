package top.chorg.iCommerce.response.debug;

import com.google.gson.Gson;
import top.chorg.iCommerce.api.Config;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SetConfigFile extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        // 设置响应内容类型
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        Gson gson = new Gson();
        Config conf = new Config();

        conf.SYSTEM_NAME = "iCommerce";
        try {
            conf = gson.fromJson(new FileReader(this.getServletContext().getRealPath("config.json")), Config.class);
        } catch (FileNotFoundException e) {
            out.println("错误：无法找到config.json文件");
            e.printStackTrace();
        }

        // 实际的逻辑是在这里

        out.println("所有操作已完成");
    }
}
