package top.chorg.icommerce.service;

import javax.servlet.http.HttpSession;

public interface AuthService {

    /**
     * Authenticates the customer. If success, a session will be setup, cookie will be in place.
     *
     * @param username The username.
     * @param password MD5 Hashed password, length = 32.
     * @return The id of the customer. -1 if not successful.
     */
    int authenticateCustomer(String username, String password);

    /**
     * Authenticates the admin. If success, a session will be setup, cookie will be in place.
     *
     * @param username The username.
     * @param password MD5 Hashed password, length = 32.
     * @return The id of the admin. -1 if not successful.
     */
    int authenticateAdmin(HttpSession session, String username, String password);

    /**
     * Debug preserved service.
     */
    void debugService();

}
