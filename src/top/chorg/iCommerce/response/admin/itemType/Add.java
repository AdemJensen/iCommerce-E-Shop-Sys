package top.chorg.iCommerce.response.admin.itemType;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.database.ItemData;
import top.chorg.iCommerce.function.Request;

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
            String typeName = request.getParameter("type_name");
            int cover = Integer.parseInt(request.getParameter("cover"));
            String typeComment = request.getParameter("type_comment");
            Database database = Database.connect(Config.readConfFromFile(request));
            switch (ItemData.addItemType(database, typeName, cover, typeComment)) {
                case 0:
                    response.sendRedirect(Request.getLastVis(request));
                    return;
                case 1:
                    res_type = "error";
                    res_str = "添加失败";
                    res_hint = "数据库未受到影响<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
                default:
                    res_type = "error";
                    res_str = "添加失败";
                    res_hint = "出现未知错误，请联系主管理。<a href='" + Request.getLastVis(request) + "'>点此返回</a>";
                    break;
            }
            database.database.close();
        } catch (Exception e) {
            res_type = "error";
            res_str = "出现错误";
            res_hint += "[严重] 错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "商品类别管理", res_str, res_hint);
    }
}
