package top.chorg.icommerce.bean.dto;

import top.chorg.icommerce.common.enums.CustomerGender;
import top.chorg.icommerce.common.enums.CustomerType;

import java.util.Date;

public class Customer {

    private final int id;
    private final String username;
    private final String name;
    private final String nickname;
    private final CustomerGender gender;
    private final Date birthday;
    private final String email;
    private final Date regTime;
    private final CustomerType type;

    public Customer(int id, String username, String name, String nickname, CustomerGender gender, Date birthday, String email, Date regTime, CustomerType type) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.regTime = regTime;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public CustomerGender getGender() {
        return gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegTime() {
        return regTime;
    }

    public CustomerType getType() {
        return type;
    }
}
