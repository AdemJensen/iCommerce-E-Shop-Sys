package top.chorg.iCommerce.response.auth;

import top.chorg.iCommerce.function.Request;
import top.chorg.iCommerce.function.auth.AuthInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Exit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String res_type = "success";
        String res_str;
        String res_hint = "";
        try {
            HttpSession session = request.getSession(false);
            if (!AuthInfo.isUser(session)) {
                res_type = "warning";
                res_str = "无效的访问";
                res_hint = "您还未登录，无需退出登录";
            } else {
                res_str = "退出成功";
                if (AuthInfo.isAdmin(session)) {
                    res_hint = "您已成功退出【管理员】身份";
                    session.removeAttribute("a_id");
                    session.removeAttribute("a_name");
                } else {
                    res_hint = "您已成功退出登录";
                    session.removeAttribute("c_id");
                    session.removeAttribute("c_name");
                }
            }
        } catch (Exception e) {
            res_type = "error";
            res_str = "出现错误";
            res_hint += "[严重] 错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "退出登录", res_str, res_hint);
    }
}
