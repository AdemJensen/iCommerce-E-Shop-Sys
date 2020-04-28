package top.chorg.iCommerce.response.auth;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.database.AuthData;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.function.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterSubmit extends HttpServlet {
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
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Database database = Database.connect(Config.readConfFromFile(request));
            switch (AuthData.customerRegister(database, request.getSession(false), email, username, password)) {
                case 0:
                    res_type = "success";
                    res_str = "注册成功";
                    res_hint = "您已经成功注册，欢迎来到" + Config.readConfFromFile(request).SYSTEM_NAME + "。";
                    break;
                case 1:
                    res_type = "error";
                    res_str = "注册失败";
                    res_hint = "您已经登录，请先退出再试<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
                case 2:
                    res_type = "error";
                    res_str = "注册失败";
                    res_hint = "您已经登录【管理员】账户，请先退出再试<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
                case 3:
                    res_type = "error";
                    res_str = "注册失败";
                    res_hint = "用户名已经被注册过，请<a href='" + request.getContextPath() + "/register'>点此返回</a>";
                    break;
                case 4:
                    res_type = "error";
                    res_str = "注册失败";
                    res_hint = "邮箱已经被使用过，请<a href='" + request.getContextPath() + "/register'>点此返回</a>";
                    break;
                default:
                    res_type = "error";
                    res_str = "注册失败";
                    res_hint = "出现未知错误，请联系管理员。<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
            }
            database.database.close();
        } catch (Exception e) {
            res_type = "error";
            res_str = "出现错误";
            res_hint += "[严重] 错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, false, res_type, "注册", res_str, res_hint, request.getContextPath() + "/login");

    }
}
