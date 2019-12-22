package top.chorg.iCommerce.response.admin;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.database.AuthData;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.function.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSubmit extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String ori;
        String res_type;
        String res_str;
        String res_hint = "";
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Database database = Database.connect(Config.readConfFromFile(request));
            switch (AuthData.adminLogin(database, request.getSession(false), username, password)) {
                case 0:
                    res_type = "success";
                    res_str = "登录成功";
                    res_hint = "您已经成功登录";
                    break;
                case 1:
                    res_type = "error";
                    res_str = "登录请求被拒绝";
                    res_hint = "您已经登录，请先退出再试<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
                case 2:
                    res_type = "error";
                    res_str = "登录请求被拒绝";
                    res_hint = "您已经登录【普通】账户，请先退出再试<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
                case 3:
                    res_type = "error";
                    res_str = "登录请求被拒绝";
                    res_hint = "用户名或密码错误，请<a href='" + request.getContextPath() + "/admin/login'>点此返回</a>";
                    break;
                default:
                    res_type = "error";
                    res_str = "登录失败";
                    res_hint = "出现未知错误，请联系主管理。<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
            }
            database.database.close();
        } catch (Exception e) {
            res_type = "error";
            res_str = "出现错误";
            res_hint += "[严重] 错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "登录", res_str, res_hint);
    }
}
