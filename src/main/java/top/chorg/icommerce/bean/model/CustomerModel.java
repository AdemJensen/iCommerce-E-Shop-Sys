package top.chorg.icommerce.bean.model;

import top.chorg.icommerce.bean.dto.Admin;
import top.chorg.icommerce.bean.dto.Customer;
import top.chorg.icommerce.dao.AuthDao;

import java.util.Date;

public class CustomerModel {

    private final int c_id;
    private final String c_username;
    private final String c_name;
    private final String c_nickname;
    private final String c_gender;
    private final Date c_birthday;
    private final String c_email;
    private final Date c_reg_time;
    private final String c_type;

    public CustomerModel(int c_id, String c_username, String c_name, String c_nickname, String c_gender, Date c_birthday, String c_email, Date c_reg_time, String c_type) {
        this.c_id = c_id;
        this.c_username = c_username;
        this.c_name = c_name;
        this.c_nickname = c_nickname;
        this.c_gender = c_gender;
        this.c_birthday = c_birthday;
        this.c_email = c_email;
        this.c_reg_time = c_reg_time;
        this.c_type = c_type;
    }

    public Customer getDto(AuthDao authDao) {
        return new Customer(
                c_id, c_username, c_name, c_nickname,
                authDao.translateCustomerGender(c_gender),
                c_birthday, c_email, c_reg_time,
                authDao.translateCustomerType(c_type)
        );
    }

}
