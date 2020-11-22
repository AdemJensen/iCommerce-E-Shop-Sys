package top.chorg.icommerce.dao;

import top.chorg.icommerce.bean.dto.Admin;
import top.chorg.icommerce.bean.dto.Customer;
import top.chorg.icommerce.common.enums.AdminType;
import top.chorg.icommerce.common.enums.CustomerGender;
import top.chorg.icommerce.common.enums.CustomerType;

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
     * Get information DTO with id.
     *
     * @param id The id of targeted admin.
     * @return The Customer DTO.
     */
    Customer getCustomerInfoById(int id);

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

    /**
     * Translates the CustomerType Enum into String for DB storage.
     *
     * @param customerType CustomerType Enum.
     * @return String for DB storage.
     */
    String translateCustomerType(CustomerType customerType);

    /**
     * Translates the String for DB storage into CustomerType Enum.
     *
     * @param customerType String for DB storage.
     * @return CustomerType Enum.
     */
    CustomerType translateCustomerType(String customerType);

    /**
     * Translates the CustomerGender Enum into String for DB storage.
     *
     * @param customerGender CustomerGender Enum.
     * @return String for DB storage.
     */
    String translateCustomerGender(CustomerGender customerGender);

    /**
     * Translates the String for DB storage into CustomerGender Enum.
     *
     * @param customerGender String for DB storage.
     * @return CustomerGender Enum.
     */
    CustomerGender translateCustomerGender(String customerGender);

    /**
     * Get the nickname of the customer.
     *
     * @param customerId The id of customer.
     * @return The nickname of customer.
     */
    String getCustomerNickname(int customerId);

    /**
     * Get the nickname of the admin.
     *
     * @param adminId The id of admin.
     * @return The nickname of admin.
     */
    String getAdminNickname(int adminId);

}
