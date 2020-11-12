package top.chorg.icommerce.dao;

import top.chorg.icommerce.bean.dto.Admin;
import top.chorg.icommerce.bean.dto.AdminType;

public interface AuthDao {

    int authenticateCustomer(String username, String password);

    int authenticateAdmin(String username, String password);

    int addCustomer(String email, String username, String password);

    /**
     * Create an admin user.
     * Notify that this method doesn't contains any logic checks, only basic DB checks.
     * Previous checks must be made ahead of this operation in the service layer.
     *
     * @param email The email to receive information.
     * @param username The username for login.
     * @param password The password.
     * @param adminType The type of admin.
     * @return The id of created admin user.
     */
    int addAdmin(String email, String username, String password, AdminType adminType);

    /**
     * Check if the admin username is already in use.
     *
     * @param username Username to check.
     * @return <b>True</b> if already in use.
     */
    boolean isAdminUsernameTaken(String username);

    /**
     * Check if the customer username is already in use.
     *
     * @param username Username to check.
     * @return <b>True</b> if already in use.
     */
    boolean isCustomerUsernameTaken(String username);

    /**
     * Get information DTO with id.
     *
     * @param id The id of targeted admin.
     * @return The Admin DTO.
     */
    Admin getAdminInfoById(int id);

    /**
     * Translates the AdminType Enum into String for DB storage.
     *
     * @param adminType AdminType Enum.
     * @return String for DB storage.
     */
    String translateAdminType(AdminType adminType);

    /**
     * Translates the String for DB storage into AdminType Enum.
     *
     * @param adminType String for DB storage.
     * @return AdminType Enum.
     */
    AdminType translateAdminType(String adminType);


}
