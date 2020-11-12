package top.chorg.icommerce.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import top.chorg.icommerce.bean.dto.Admin;
import top.chorg.icommerce.bean.dto.AdminType;
import top.chorg.icommerce.bean.model.AdminModel;
import top.chorg.icommerce.dao.AuthDao;
import top.chorg.icommerce.service.impl.AuthServiceImpl;

import java.sql.*;

@Repository
public class AuthDaoImpl implements AuthDao {

    private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final JdbcTemplate dbTemplate;

    public AuthDaoImpl(JdbcTemplate dbTemplate) {
        this.dbTemplate = dbTemplate;
    }

    @Override
    public int authenticateCustomer(String username, String password) {
        try {
            return -1;
        } catch (DataAccessException | NullPointerException e) {
            LOG.debug(
                    "Got `{}` exception when authenticating customer, param is (\"{}\", \"{}\")",
                    e.getMessage(), username, password
            );
            return -1;  // 出现未知错误
        }
    }

    @Override
    public int authenticateAdmin(String username, String password) {
        try {
            Integer id = dbTemplate.queryForObject(
                    "SELECT a_id FROM admins WHERE a_username=? AND password=?",
                    new Object[] {username, password},
                    Integer.class
            );
            if (id == null) throw new NullPointerException();
            return id;
        } catch (DataAccessException | NullPointerException e) {
            LOG.debug(
                    "Got `{}` exception when authenticating customer, param is (\"{}\", \"{}\")",
                    e.toString(), username, password
            );
            return -1;  // 出现错误，如密码错误，或者未知错误
        }
    }

    @Override
    public int addCustomer(String email, String username, String password) {
        return 0;
    }

    @Override
    public int addAdmin(String email, String username, String password, AdminType adminType) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            dbTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO admins (a_username, password, a_email, a_nickname, a_type, a_reg_time)" +
                                " VALUES (?, ?, ?, a_username, ?, NOW())",
                        PreparedStatement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setString(4, translateAdminType(adminType));
                return ps;
            }, keyHolder);
            Number id = keyHolder.getKey();
            if (id == null) throw new NullPointerException();
            return (int) id;
        } catch (DataAccessException | NullPointerException e) {
            LOG.debug(
                    "Got `{}` exception when creating admin, param is (\"{}\", \"{}\", \"{}\", \"{}\")",
                    e.toString(), email, username, password, translateAdminType(adminType)
            );
            return -1;  // 出现错误，如用户名已存在，或者未知错误
        }
    }

    @Override
    public boolean isAdminUsernameTaken(String username) {
        return false;
    }

    @Override
    public boolean isCustomerUsernameTaken(String username) {
        return false;
    }

    @Override
    public Admin getAdminInfoById(int id) {
        try {
            AdminModel adminModel = dbTemplate.queryForObject(
                    "SELECT a_id, a_username, a_email, a_nickname, a_type, a_reg_time FROM admins WHERE a_id=?",
                    new Object[] {id},
                    AdminModel.class
            );
            if (adminModel == null) throw new NullPointerException();
            return adminModel.getDto(this);
        } catch (DataAccessException | NullPointerException e) {
            LOG.error(
                    "Got `{}` exception when getAdminInfoById, param is (\"{}\")",
                    e.toString(), id
            );
            return null;  // 出现错误，一般是或者未知错误
        }
    }

    @Override
    public String translateAdminType(AdminType adminType) {
        switch (adminType) {
            case RootAdmin:
                return "R";
            case NormalAdmin:
                return "N";
            default:
                return "B";
        }
    }

    @Override
    public AdminType translateAdminType(String adminType) {
        switch (adminType) {
            case "R":
                return AdminType.RootAdmin;
            case "N":
                return AdminType.NormalAdmin;
            default:
                return AdminType.BannedAdmin;
        }
    }

}
