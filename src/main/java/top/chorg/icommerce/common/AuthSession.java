package top.chorg.icommerce.common;

import org.springframework.stereotype.Component;
import top.chorg.icommerce.bean.dto.Admin;
import top.chorg.icommerce.dao.AuthDao;

import javax.servlet.http.HttpSession;

@Component
public class AuthSession {

    private final AuthDao authDao;

    public AuthSession(AuthDao authDao) {
        this.authDao = authDao;
    }

    public boolean createAuthSessionForAdmin(HttpSession session, int adminId) {
        Admin admin = authDao.getAdminInfoById(adminId);
        if (admin == null) return false;
        session.setAttribute("a_id", admin.getId());
        session.setAttribute("a_level", admin.getAdminType());
        session.setAttribute("a_name", admin.getNickname());
        return true;
    }

    public boolean createAuthSessionForCustomer(HttpSession session, int customerId) {
        // TODO: Customer auth session.
        return false;
    }

    public boolean isAdminAuthSessionValid(HttpSession session) {
        return session.getAttribute("a_id") != null;
    }

    public boolean isCustomerAuthSessionValid(HttpSession session) {
        return session.getAttribute("c_id") != null;
    }

    public int getAdminUserId(HttpSession session) {
        return isAdminAuthSessionValid(session) ? (int) session.getAttribute("a_id") : -1;
    }

    public int getCustomerUserId(HttpSession session) {
        return isCustomerAuthSessionValid(session) ? (int) session.getAttribute("c_id") : -1;
    }

    public void clearAdminSession(HttpSession session) {
        session.removeAttribute("a_id");
        session.removeAttribute("a_level");
        session.removeAttribute("a_name");
    }

    public void clearCustomerSession(HttpSession session) {
        // TODO: Customer auth session.
    }

}
