package top.chorg.iCommerce.response.debug;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.function.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SetConfigDefault extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String res_type;
        String res_str;
        String res_hint;
        // 设置响应内容类型
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Config conf = new Config();

            conf.SYSTEM_NAME = "iCommerce";
            conf.USE_MODIFIED_INDEX = false;
            conf.MODIFIED_INDEX_FILE = "VOID";

            conf.CHAT_SYSTEM = "<script src=\"//code.tidio.co/yh2asfkcq8zmjvjh4reb2p1v9nsuawwk.js\" async></script>";

            conf.DATABASE_ADDR = "127.0.0.1";
            conf.DATABASE_PORT = 3306;
            conf.DATABASE_USERNAME = "root";
            conf.DATABASE_PASSWORD = "rtl20000214";
            conf.DATABASE_SCHEMA = "iCommerce";
            conf.DATABASE_USE_UNICODE = true;
            conf.DATABASE_ENCODING = "UTF-8";
            conf.DATABASE_TIMEZONE = "";
            conf.DATABASE_USE_SSL = false;
            conf.PUBLIC_KEY_RETRIEVAL = true;

            conf.DATABASE_TABLE_ADMIN = "admin";
            conf.DATABASE_TABLE_CART = "cart";
            conf.DATABASE_TABLE_ITEM = "item";
            conf.DATABASE_TABLE_ITEM_TYPE = "item_type";
            conf.DATABASE_TABLE_ITEM_TYPE_BELONG = "item_type_belong";
            conf.DATABASE_TABLE_ORDER = "order";
            conf.DATABASE_TABLE_ORDER_BELONG = "order_belong";
            conf.DATABASE_TABLE_SHIPPING = "shipping";
            conf.DATABASE_TABLE_USER = "user";

            conf.saveToFile(this.getServletContext().getRealPath("config.json"));
            conf.saveToFile("/Users/jensen/Documents/Projects/IdeaProjects/WebTech-02/web/config.json");
            res_type = "success";
            res_str = "config.json初始化设置成功";
            res_hint = "已成功为开发环境和部署环境设置以下内容：" + gson.toJson(conf);
        } catch (Exception e) {
            res_type = "danger";
            res_str = "设置失败";
            res_hint = "出现未知错误：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, true, res_type, "config.json初始化", res_str, res_hint, "");
    }
}
