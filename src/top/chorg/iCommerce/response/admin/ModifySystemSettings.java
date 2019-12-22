package top.chorg.iCommerce.response.admin;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.function.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class ModifySystemSettings extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String ip = request.getRemoteAddr();
        if(!ip.equals("127.0.0.1") && !ip.equals("0:0:0:0:0:0:0:1")){
            Request.becomeInfoPage(request, response, "error", "错误", "您的网络环境不符合要求",
                    "由于安全策略，本页面只能在内网环境下才可以访问。");
            return;
        }
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String res_type;
        String res_str;
        String res_hint = "";
        try {
            Config conf = new Config();
            conf.SYSTEM_NAME = request.getParameter("SYSTEM_NAME");
            conf.USE_MODIFIED_INDEX = request.getParameter("USE_MODIFIED_INDEX").equals("true");
            conf.MODIFIED_INDEX_FILE = request.getParameter("MODIFIED_INDEX_FILE");

            conf.CHAT_SYSTEM = request.getParameter("CHAT_SYSTEM");

            conf.DATABASE_ADDR = request.getParameter("DATABASE_ADDR");
            conf.DATABASE_PORT = Integer.parseInt(request.getParameter("DATABASE_PORT"));
            conf.DATABASE_USERNAME = request.getParameter("DATABASE_USERNAME");
            conf.DATABASE_PASSWORD = request.getParameter("DATABASE_PASSWORD");
            conf.DATABASE_SCHEMA = request.getParameter("DATABASE_SCHEMA");
            conf.DATABASE_USE_UNICODE = request.getParameter("DATABASE_USE_UNICODE").equals("true");
            conf.DATABASE_ENCODING = request.getParameter("DATABASE_ENCODING");
            conf.DATABASE_TIMEZONE = request.getParameter("DATABASE_TIMEZONE");
            conf.DATABASE_USE_SSL = request.getParameter("DATABASE_USE_SSL").equals("true");
            conf.PUBLIC_KEY_RETRIEVAL = request.getParameter("PUBLIC_KEY_RETRIEVAL").equals("true");

            conf.DATABASE_TABLE_ADMIN = request.getParameter("DATABASE_TABLE_ADMIN");
            conf.DATABASE_TABLE_CART = request.getParameter("DATABASE_TABLE_CART");
            conf.DATABASE_TABLE_ITEM = request.getParameter("DATABASE_TABLE_ITEM");
            conf.DATABASE_TABLE_ITEM_TYPE = request.getParameter("DATABASE_TABLE_ITEM_TYPE");
            conf.DATABASE_TABLE_ITEM_TYPE_BELONG = request.getParameter("DATABASE_TABLE_ITEM_TYPE_BELONG");
            conf.DATABASE_TABLE_ORDER = request.getParameter("DATABASE_TABLE_ORDER");
            conf.DATABASE_TABLE_ORDER_BELONG = request.getParameter("DATABASE_TABLE_ORDER_BELONG");
            conf.DATABASE_TABLE_SHIPPING = request.getParameter("DATABASE_TABLE_SHIPPING");
            conf.DATABASE_TABLE_USER = request.getParameter("DATABASE_TABLE_USER");

            res_type = "success";
            res_str = "设置成功";

            if (conf.USE_MODIFIED_INDEX) {
                if (!new File(Config.getCustomFileAbsolutePath(request, conf.MODIFIED_INDEX_FILE)).exists()) {
                    res_type = "danger";
                    res_str = "设置失败";
                    res_hint += "[严重] 无法找到文件custom/" + conf.MODIFIED_INDEX_FILE + "<br/>";
                }
            }

            if (conf.DATABASE_PORT < 1024) {
                if (!res_type.equals("danger")) res_type = "warning";
                res_hint += "[警告] 0 - 1023端口为系统保留端口，不推荐作为数据库访问端口<br/>";
            }

            try {
                Database database = Database.connect(conf);
                System.out.println("数据库连接成功");
                database.database.close();
            } catch (Exception e) {
                e.printStackTrace();
                res_type = "danger";
                res_str = "设置失败";
                res_hint += "[严重] 连接或操作数据库时出现错误(" + e.getMessage() + ")" + "<br/>";
            }

            if (!res_type.equals("danger")) {
                conf.saveToFile(this.getServletContext().getRealPath("config.json"));
                if (!res_type.equals("warning")) res_hint = "所有设置均已成功应用";
            }

        } catch (Exception e) {
            res_type = "error";
            res_str = "设置失败";
            res_hint += "[严重] 应用设置时出现错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "系统设置", res_str, res_hint);
    }
}
