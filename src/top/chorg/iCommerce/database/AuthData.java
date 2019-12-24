package top.chorg.iCommerce.database;

import top.chorg.iCommerce.api.Customer;

import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthData {

    public static int customerLogin(Database database, HttpSession session, String username, String password) {
        try {
            if (session.getAttribute("c_id") != null) return 1; // 已经登录
            if (session.getAttribute("a_id") != null) return 2; // 已经登录管理员账户
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT id, username, disabled FROM `" + database.conf.DATABASE_TABLE_USER + "` WHERE username=? AND password=?"
            );
            state.setString(1, username);
            state.setString(2, password);
            ResultSet res = state.executeQuery();
            if (!res.next()) return 3;
            if (res.getShort("disabled") != 0) return 4;    // 用户已经被封禁
            session.setAttribute("c_id", res.getInt("id"));
            session.setAttribute("c_name", res.getString("username"));
            return 0;   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while validating user (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int adminLogin(Database database, HttpSession session, String username, String password) {
        try {
            if (session.getAttribute("a_id") != null) return 1; // 已经登录
            if (session.getAttribute("c_id") != null) return 2; // 已经登录普通账户
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT id, level, username FROM `" + database.conf.DATABASE_TABLE_ADMIN + "` WHERE username=? AND password=?"
            );
            state.setString(1, username);
            state.setString(2, password);
            ResultSet res = state.executeQuery();
            if (!res.next()) return 3;
            session.setAttribute("a_id", res.getInt("id"));
            session.setAttribute("a_level", res.getInt("level"));
            session.setAttribute("a_name", res.getString("username"));
            return 0;   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while validating admin (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int customerRegister(Database database, HttpSession session, String email, String username, String password) {
        try {
            if (session.getAttribute("c_id") != null) return 1; // 已经登录
            if (session.getAttribute("a_id") != null) return 2; // 已经登录管理员账户
            PreparedStatement state = database.database.prepareStatement("SELECT id FROM `" + database.conf.DATABASE_TABLE_USER + "` WHERE username=?");
            state.setString(1, username);
            if (state.executeQuery().next()) return 3;
            state = database.database.prepareStatement("SELECT id FROM `" + database.conf.DATABASE_TABLE_USER + "` WHERE email=?");
            state.setString(1, email);
            if (state.executeQuery().next()) return 4;
            state = database.database.prepareStatement("INSERT INTO `" + database.conf.DATABASE_TABLE_USER + "` (username, password, email, balance) VALUES (?, ?, ?, ?)");
            state.setString(1, username);
            state.setString(2, password);
            state.setString(3, email);
            state.setInt(4, 1000000);
            if (state.executeUpdate() > 0) return 0;
            else throw new SQLException("数据库未受到影响");
        } catch (SQLException e) {
            System.err.printf("Error while validating user (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int adminRegister(Database database, HttpSession session, String email, String username, String password, int level) {
        try {
            if (session.getAttribute("c_id") != null) return 1; // 已经登录
            if (session.getAttribute("a_id") != null) return 2; // 已经登录管理员账户
            PreparedStatement state = database.database.prepareStatement("SELECT id FROM `" + database.conf.DATABASE_TABLE_USER + "` WHERE username=?");
            state.setString(1, username);
            if (state.executeQuery().next()) return 3;
            state = database.database.prepareStatement("SELECT id FROM `" + database.conf.DATABASE_TABLE_USER + "` WHERE email=?");
            state.setString(1, email);
            if (state.executeQuery().next()) return 4;
            state = database.database.prepareStatement("INSERT INTO `" + database.conf.DATABASE_TABLE_USER + "` (username, password, email) VALUES (?, ?, ?)");
            state.setString(1, username);
            state.setString(2, password);
            state.setString(3, email);
            if (state.executeUpdate() > 0) return 0;
            else throw new SQLException("数据库未受到影响");
        } catch (SQLException e) {
            System.err.printf("Error while validating user (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static Customer getCustomerById(Database database, int id) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT username, id, email, balance, registerTime FROM `" + database.conf.DATABASE_TABLE_USER + "` WHERE id=?"
            );
            state.setInt(1, id);
            ResultSet res = state.executeQuery();
            if (!res.next()) return null;
            return new Customer(
                    res.getInt("id"),
                    res.getString("username"),
                    res.getString("email"),
                    res.getDouble("balance"),
                    res.getTimestamp("registerTime")
            );   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while getting user (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static double getCustomerBalance(Database database, int id) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT balance FROM `" + database.conf.DATABASE_TABLE_USER + "` WHERE id=?"
            );
            state.setInt(1, id);
            ResultSet res = state.executeQuery();
            if (!res.next()) return 0;
            return res.getDouble("balance");   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while getting user (%s)\n", e.getMessage());
            return 0;  // 出现未知错误
        }
    }

    public static int subCustomerBalance(Database database, int customerId, double price) {
        try {
            double balance = getCustomerBalance(database, customerId);
            if (balance < price) return 1;
            PreparedStatement state = database.database.prepareStatement(
                    "UPDATE `" + database.conf.DATABASE_TABLE_USER + "` SET balance=? WHERE id=?"
            );
            state.setDouble(1, balance - price);
            state.setInt(2, customerId);
            int res = state.executeUpdate();
            return res > 0 ? 0 : 2;
        } catch (SQLException e) {
            System.err.printf("Error while subtracting balance (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

}
