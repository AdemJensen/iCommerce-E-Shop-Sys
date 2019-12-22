package top.chorg.iCommerce.response.order;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.api.Order;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.database.OrderData;
import top.chorg.iCommerce.function.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Complete extends HttpServlet {
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
            int target = Integer.parseInt(request.getParameter("completeTarget"));
            Database database = Database.connect(Config.readConfFromFile(request));
            switch (OrderData.setPhase(database, target, 3) + OrderData.doComplete(database, target)) {
                case 0:
                    res_type = "success";
                    res_str = "确认收货成功";
                    res_hint = "交易已成功！感谢您对我们的支持！";
                    break;
                case 1:
                    res_type = "error";
                    res_str = "操作失败";
                    res_hint = "操作失败，数据库未受影响。";
                    break;
                default:
                    res_type = "error";
                    res_str = "操作失败";
                    res_hint = "出现未知错误，请联系主管理。<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
            }
            database.database.close();
        } catch (Exception e) {
            res_type = "error";
            res_str = "出现错误";
            res_hint += "[严重] 错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "完成交易", res_str, res_hint);
    }
}
