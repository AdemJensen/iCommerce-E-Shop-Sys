package top.chorg.icommerce.bean.dto;

import top.chorg.icommerce.common.enums.AdminType;

import java.util.Date;

public class Admin {

    private final int id;
    private final String username;
    private final String email;
    private final String nickname;
    private final AdminType adminType;
    private final Date registerTime;

    public Admin(int id, String username, String email, String nickname, AdminType adminType, Date registerTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.adminType = adminType;
        this.registerTime = registerTime;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public AdminType getAdminType() {
        return adminType;
    }

    public Date getRegisterTime() {
        return registerTime;
    }
}
