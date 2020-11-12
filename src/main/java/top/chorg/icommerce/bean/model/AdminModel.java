package top.chorg.icommerce.bean.model;

import top.chorg.icommerce.bean.dto.Admin;
import top.chorg.icommerce.dao.AuthDao;

import java.util.Date;

public class AdminModel {

    private final int a_id;
    private final String a_username;
    private final String a_email;
    private final String a_nickname;
    private final String a_type;
    private final Date a_reg_time;

    public AdminModel(int a_id, String a_username, String a_email, String a_nickname, String a_type, Date a_reg_time) {
        this.a_id = a_id;
        this.a_username = a_username;
        this.a_email = a_email;
        this.a_nickname = a_nickname;
        this.a_type = a_type;
        this.a_reg_time = a_reg_time;
    }

    public Admin getDto(AuthDao authDao) {
        return new Admin(a_id, a_username, a_email, a_nickname,
                authDao.translateAdminType(a_type), a_reg_time);
    }

}
