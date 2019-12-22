package top.chorg.iCommerce.response.order;

import com.google.gson.Gson;
import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.api.OrderSubmitItem;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.database.ItemData;
import top.chorg.iCommerce.database.OrderData;
import top.chorg.iCommerce.function.Request;
import top.chorg.iCommerce.function.auth.AuthInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Add extends HttpServlet {
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
            Gson gson = new Gson();
            int customer = AuthInfo.getCustomerId(request.getSession(false));
            int shipping = Integer.parseInt(request.getParameter("address"));
            OrderSubmitItem[] oc = gson.fromJson(request.getParameter("orderContent"), OrderSubmitItem[].class);
            boolean isCart = request.getParameter("isCart").equals("true");
            Database database = Database.connect(Config.readConfFromFile(request));
            switch (OrderData.addOrder(database, customer, shipping, oc)) {
                case 0:
                    res_type = "success";
                    res_str = "下单成功";
                    res_hint = "您已成功下单，宝贝马上就会飞到您的手里啦！";
                    for (OrderSubmitItem orderSubmitItem : oc) {
                        ItemData.setCartItem(database, customer, orderSubmitItem.id, 0);
                    }
                    break;
                case 1:
                    res_type = "error";
                    res_str = "余额不足";
                    res_hint = "您的余额不足，请充值后再下单。";
                    break;
                case 2:
                    res_type = "error";
                    res_str = "订单无效";
                    res_hint = "您的订单中有无效商品。";
                    break;
                default:
                    res_type = "error";
                    res_str = "订单提交失败";
                    res_hint = "出现未知错误，请联系主管理。<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
            }
            database.database.close();
        } catch (Exception e) {
            res_type = "error";
            res_str = "出现错误";
            res_hint += "[严重] 错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "提交订单", res_str, res_hint);
    }
}
