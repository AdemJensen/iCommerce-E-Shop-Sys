package top.chorg.iCommerce.response.cart;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.database.ItemData;
import top.chorg.iCommerce.function.auth.AuthInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Add extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();
        try {
            int itemId = Integer.parseInt(request.getParameter("item"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Database database = Database.connect(Config.readConfFromFile(request));
            if (ItemData.addCartItem(database, AuthInfo.getCustomerId(request.getSession(false)), itemId, quantity) == 0) {
                out.write("OK".getBytes(StandardCharsets.UTF_8));
            } else {
                out.write("ERR".getBytes(StandardCharsets.UTF_8));
            }
            database.database.close();
        } catch (Exception e) {
            out.write("ERR".getBytes(StandardCharsets.UTF_8));
        }
    }
}
