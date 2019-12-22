package top.chorg.iCommerce.response.admin.item;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.database.ItemData;
import top.chorg.iCommerce.function.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Edit extends HttpServlet {
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
            int target = Integer.parseInt(request.getParameter("edit_item_target"));
            String itemName = request.getParameter("edit_name");
            int cover = Integer.parseInt(request.getParameter("edit_cover"));
            String itemDetailPage = request.getParameter("edit_detail_page");
            double itemOldPrice = Double.parseDouble(request.getParameter("edit_price_old"));
            double itemNewPrice = Double.parseDouble(request.getParameter("edit_price_new"));
            int itemQuantity = Integer.parseInt(request.getParameter("edit_quantity"));
            String[] itemTypeS = request.getParameterValues("edit_type");
            int[] itemType = new int[itemTypeS.length];
            for (int i = 0; i < itemType.length; i++) {
                itemType[i] = Integer.parseInt(itemTypeS[i]);
            }
            Database database = Database.connect(Config.readConfFromFile(request));
            switch (ItemData.editItem(database, target, itemName, cover, itemDetailPage, itemOldPrice, itemNewPrice, itemQuantity, itemType)) {
                case 0:
                    response.sendRedirect(Request.getLastVis(request));
                    return;
                case 1:
                    res_type = "error";
                    res_str = "编辑失败";
                    res_hint = "数据库未受到影响<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
                default:
                    res_type = "error";
                    res_str = "编辑失败";
                    res_hint = "出现未知错误，请联系主管理。<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
            }
            database.database.close();
        } catch (Exception e) {
            res_type = "error";
            res_str = "出现错误";
            res_hint += "[严重] 错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "商品管理", res_str, res_hint);
    }
}
