package top.chorg.icommerce.common;

import org.springframework.stereotype.Component;
import top.chorg.icommerce.bean.dto.Admin;
import top.chorg.icommerce.common.enums.UserType;
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

    /**
     * Get the nickname for the logged in customer.
     *
     * @param session Http Session.
     * @return Nickname. <b>null</b> if customer is not logged in.
     */
    public String getCustomerNickname(HttpSession session) {
        if (isCustomerAuthSessionValid(session)) {
            return authDao.getCustomerNickname(getCustomerUserId(session));
        }
        return null;
    }

    /**
     * Get the nickname for the logged in admin.
     *
     * @param session Http Session.
     * @return Nickname. <b>null</b> if admin is not logged in.
     */
    public String getAdminNickname(HttpSession session) {
        if (isAdminAuthSessionValid(session)) {
            return authDao.getAdminNickname(getAdminUserId(session));
        }
        return null;
    }

    public void clearAdminSession(HttpSession session) {
        session.removeAttribute("a_id");
        session.removeAttribute("a_level");
        session.removeAttribute("a_name");
    }

    public void clearCustomerSession(HttpSession session) {
        // TODO: Customer auth session.
    }

    /**
     * Get the logged in user's type.
     *
     * @param session Http Session.
     * @return The type of logged in user.
     */
    public UserType getSessionUserType(HttpSession session) {
        if (isCustomerAuthSessionValid(session)) return UserType.Customer;
        if (isAdminAuthSessionValid(session)) return UserType.Admin;
        return null;
    }

    /**
     * Get the logged in user's id. No matter admin or customer.
     *
     * @param session Http Session.
     * @return The id of logged in user.
     * Notice that the id might be identical when you have both an admin account and customer account.
     * A pair of (userType, userId) can decide a unique user in the system. Single userId won't work.
     */
    public int getSessionUserId(HttpSession session) {
        if (isCustomerAuthSessionValid(session)) return getCustomerUserId(session);
        if (isAdminAuthSessionValid(session)) return getAdminUserId(session);
        return -1;
    }

    /**
     * Get the displayable name for the logged in user.
     *
     * @param session  Http Session.
     * @return Displayable name. <b>null</b> if user is not logged in.
     */
    public String getSessionUserDisplayName(HttpSession session) {
        if (isCustomerAuthSessionValid(session)) {
            return getCustomerNickname(session);
        }
        if (isAdminAuthSessionValid(session)) {
            return getAdminNickname(session);
        }
        return null;
    }

}
