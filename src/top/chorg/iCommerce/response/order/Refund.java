package top.chorg.iCommerce.response.order;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.database.OrderData;
import top.chorg.iCommerce.function.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Refund extends HttpServlet {
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
            int target = Integer.parseInt(request.getParameter("refundTarget"));
            Database database = Database.connect(Config.readConfFromFile(request));
            int ops;
            if (OrderData.getOrderStatus(database, target) == 0) {
                ops = OrderData.setPhase(database, target, 6);
            } else {
                ops = OrderData.setPhase(database, target, 4);
            }
            if (ops == 0) {
                response.sendRedirect(Request.getLastVis(request));
                return;
            } else {
                res_type = "error";
                res_str = "操作失败";
                res_hint = "出现未知错误，请联系主管理。<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
            }
            database.database.close();
        } catch (Exception e) {
            res_type = "error";
            res_str = "出现错误";
            res_hint += "[严重] 错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "退款申请", res_str, res_hint);
    }
}
