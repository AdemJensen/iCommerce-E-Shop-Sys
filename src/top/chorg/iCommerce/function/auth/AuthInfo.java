package top.chorg.iCommerce.function.auth;

import top.chorg.iCommerce.api.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

public class AuthInfo {
    public static boolean isUser(HttpSession session) {
        if (session == null) return false;
        return session.getAttribute("a_id") != null || session.getAttribute("c_id") != null;
    }
    public static boolean isCustomer(HttpSession session) {
        if (session == null) return false;
        return session.getAttribute("c_id") != null;
    }
    public static boolean isAdmin(HttpSession session) {
        if (session == null) return false;
        return session.getAttribute("a_id") != null;
    }
    public static String getCustomerAvatar(HttpServletRequest request) throws NullPointerException {
        HttpSession session = request.getSession(false);
        int id = (int) session.getAttribute("c_id");
        String target = request.getContextPath() + "/assets/img/avatars/c/" + id + ".png";
        if (new File(Config.getImageFileAbsolutePath(request, "avatars/c/" + id + ".png")).exists()) {
            return target;
        } else {
            return request.getContextPath() + "/assets/img/avatars/default.png";
        }
    }
    public static String getAdminAvatar(HttpServletRequest request) throws NullPointerException {
        HttpSession session = request.getSession(false);
        int id = (int) session.getAttribute("a_id");
        String target = request.getContextPath() + "/assets/img/avatars/a/" + id + ".png";
        if (new File(Config.getImageFileAbsolutePath(request, "avatars/a/" + id + ".png")).exists()) {
            return target;
        } else {
            return request.getContextPath() + "/assets/img/avatars/default.png";
        }
    }
    public static String getCustomerUsername(HttpSession session) throws NullPointerException {
        return (String) session.getAttribute("c_name");
    }
    public static String getAdminUsername(HttpSession session) throws NullPointerException {
        return (String) session.getAttribute("a_name");
    }
    public static int getAdminLevel(HttpSession session) throws NullPointerException {
        return (int) session.getAttribute("a_level");
    }
    public static int getCustomerId(HttpSession session) throws NullPointerException {
        return (int) session.getAttribute("c_id");
    }
    public static int getAdminId(HttpSession session) throws NullPointerException {
        return (int) session.getAttribute("a_id");
    }
}
