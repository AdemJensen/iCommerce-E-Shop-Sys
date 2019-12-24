package top.chorg.iCommerce.database;

import top.chorg.iCommerce.api.*;
import top.chorg.iCommerce.function.MD5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderData {

    public static int getOrderOwner(Database database, int orderId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT userId FROM `" + database.conf.DATABASE_TABLE_ORDER + "` WHERE id=?"
            );
            state.setInt(1, orderId);
            ResultSet res = state.executeQuery();
            if (!res.next()) return -1;
            return res.getInt("userId");
        } catch (SQLException e) {
            System.err.printf("Error while getting OrderOwner (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int getOrderStatus(Database database, int orderId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT `status` FROM `" + database.conf.DATABASE_TABLE_ORDER + "` WHERE id=?"
            );
            state.setInt(1, orderId);
            ResultSet res = state.executeQuery();
            if (!res.next()) return -1;
            return res.getInt("status");
        } catch (SQLException e) {
            System.err.printf("Error while getting OrderStatus (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int getOrderListLength(Database database) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                        "SELECT COUNT(*) cnt FROM `" + database.conf.DATABASE_TABLE_ORDER + "` "
            );
            ResultSet res = state.executeQuery();
            if (!res.next()) return -1;
            return res.getInt("cnt");
        } catch (SQLException e) {
            System.err.printf("Error while getting OrderListLen (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static Order[] getAllOrderList(Database database, int page, int pageSize, int sort) {
        try {
            String limit;
            if (pageSize <= 0 || page <= 0) limit = "";
            else limit = String.format("limit %d, %d ", (page - 1) * pageSize, pageSize);
            PreparedStatement state;
            if (sort == -1) {
                state = database.database.prepareStatement(
                        "SELECT * FROM `" + database.conf.DATABASE_TABLE_ORDER + "` ORDER BY orderTime DESC " + limit
                );
            } else {
                state = database.database.prepareStatement(
                        "SELECT * FROM `" + database.conf.DATABASE_TABLE_ORDER + "` WHERE `status`=? ORDER BY orderTime DESC " + limit
                );
                state.setInt(1, sort);
            }
            ResultSet res = state.executeQuery();
            ArrayList<Order> arr = new ArrayList<>();
            while (res.next()) {
                arr.add(new Order(
                        res.getInt("id"),
                        res.getString("orderNum"),
                        res.getTimestamp("orderTime"),
                        res.getInt("userId"),
                        res.getInt("shippingId"),
                        res.getInt("status"),
                        res.getString("expressNum")
                ));
            }
            return arr.toArray(new Order[0]);
        } catch (SQLException e) {
            System.err.printf("Error while getting orderList (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static Order[] getOrderList(Database database, int customerId, int page, int pageSize) {
        try {
            String limit;
            if (pageSize <= 0 || page <= 0) limit = "";
            else limit = String.format("limit %d, %d ", (page - 1) * pageSize, pageSize);
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT * FROM `" + database.conf.DATABASE_TABLE_ORDER + "` WHERE userId=? ORDER BY orderTime DESC " + limit
            );
            state.setInt(1, customerId);
            ResultSet res = state.executeQuery();
            ArrayList<Order> arr = new ArrayList<>();
            while (res.next()) {
                arr.add(new Order(
                        res.getInt("id"),
                        res.getString("orderNum"),
                        res.getTimestamp("orderTime"),
                        res.getInt("userId"),
                        res.getInt("shippingId"),
                        res.getInt("status"),
                        res.getString("expressNum")
                ));
            }
            return arr.toArray(new Order[0]);
        } catch (SQLException e) {
            System.err.printf("Error while getting orderList (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static int addOrder(Database database, int customerId, int shippingId, OrderSubmitItem[] items) {
        try {
            double balance = AuthData.getCustomerBalance(database, customerId);
            double price = 0;
            Item[] itemInfo = new Item[items.length];
            for (int i = 0; i < items.length; i++) {
                itemInfo[i] = ItemData.getItemById(database, items[i].id);
                if (itemInfo[i] == null) return 2;      // 订单中有无效商品
                if (itemInfo[i].quantity < items[i].quantity) return 2;
                price += itemInfo[i].newPrice * items[i].quantity;
            }
            if (price > balance) return 1;     // 余额不足
            PreparedStatement state = database.database.prepareStatement(
                    "INSERT INTO `" + database.conf.DATABASE_TABLE_ORDER + "` (orderTime, userId, shippingId, `status`) VALUES (NOW(), ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            state.setInt(1, customerId);
            state.setInt(2, shippingId);
            state.setInt(3, 1);
            int res = state.executeUpdate();
            if (res == 0) return 3;     // 数据库未受到影响
            ResultSet resultSet = state.getGeneratedKeys();
            resultSet.next();
            int orderId = resultSet.getInt(1);
            state = database.database.prepareStatement(
                    "UPDATE `" + database.conf.DATABASE_TABLE_ORDER + "` SET orderNum=? WHERE id=?"
            );
            state.setString(1, MD5.encode(String.valueOf(orderId)));
            state.setInt(2, orderId);
            state.executeUpdate();
            state = database.database.prepareStatement(
                    "INSERT INTO `" + database.conf.DATABASE_TABLE_ORDER_BELONG + "` VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            state.setInt(1, orderId);
            for (int i = 0; i < itemInfo.length; i++) {
                state.setInt(2, itemInfo[i].id);
                state.setInt(3, items[i].quantity);
                state.setDouble(4, itemInfo[i].newPrice);
                state.setString(5, itemInfo[i].detailPage);
                state.setInt(6, itemInfo[i].cover);
                state.setString(7, itemInfo[i].name);
                state.executeUpdate();
                ItemData.subQuantity(database, itemInfo[i].id, items[i].quantity);
            }
            AuthData.subCustomerBalance(database, customerId, price);
            return 0;
        } catch (SQLException e) {
            System.err.printf("Error while adding order (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static OrderItem[] getOrderItem(Database database, int orderId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT * FROM `" + database.conf.DATABASE_TABLE_ORDER_BELONG + "` WHERE orderId=?"
            );
            state.setInt(1, orderId);
            ResultSet res = state.executeQuery();
            ArrayList<OrderItem> arr = new ArrayList<>();
            while (res.next()) {
                arr.add(new OrderItem(
                        res.getInt("orderId"),
                        res.getInt("productId"),
                        res.getInt("amount"),
                        res.getDouble("price"),
                        res.getString("history_page"),
                        res.getInt("history_cover"),
                        res.getString("history_name")
                ));
            }
            return arr.toArray(new OrderItem[0]);
        } catch (SQLException e) {
            System.err.printf("Error while getting orderItem (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static int setPhase(Database database, int orderId, int phase) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "UPDATE `" + database.conf.DATABASE_TABLE_ORDER + "` SET `status`=? WHERE id=?"
            );
            state.setInt(1, phase);
            state.setInt(2, orderId);
            int res = state.executeUpdate();
            return res > 0 ? 0 : 1;
        } catch (SQLException e) {
            System.err.printf("Error while setting phase (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int setExpress(Database database, int orderId, String expressNum) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "UPDATE `" + database.conf.DATABASE_TABLE_ORDER + "` SET `expressNum`=? WHERE id=?"
            );
            state.setString(1, expressNum);
            state.setInt(2, orderId);
            int res = state.executeUpdate();
            return res > 0 ? 0 : 1;
        } catch (SQLException e) {
            System.err.printf("Error while setting express (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int doRefund(Database database, int orderId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT amount, price, productId FROM `" + database.conf.DATABASE_TABLE_ORDER_BELONG + "` WHERE orderId=?"
            );
            state.setInt(1, orderId);
            ResultSet res = state.executeQuery();
            double total = 0;
            while (res.next()) {
                int quantity = res.getInt("amount");
                total += quantity * res.getDouble("price");
                ItemData.subQuantity(database, res.getInt("productId"), -quantity);
            }
            return AuthData.subCustomerBalance(database, getOrderOwner(database, orderId), -total);
        } catch (SQLException e) {
            System.err.printf("Error while doing refund (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int doComplete(Database database, int orderId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT amount, productId FROM `" + database.conf.DATABASE_TABLE_ORDER_BELONG + "` WHERE orderId=?"
            );
            state.setInt(1, orderId);
            ResultSet res = state.executeQuery();
            double total = 0;
            while (res.next()) {
                int quantity = res.getInt("amount");
                ItemData.addDeals(database, res.getInt("productId"), quantity);
            }
            return 0;
        } catch (SQLException e) {
            System.err.printf("Error while doing complete (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

}
