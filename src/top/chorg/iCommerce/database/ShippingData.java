package top.chorg.iCommerce.database;

import top.chorg.iCommerce.api.Shipping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShippingData {
    public static Shipping[] getShippingList(Database database, int customerId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT * FROM `" + database.conf.DATABASE_TABLE_SHIPPING + "` WHERE userId=?"
            );
            state.setInt(1, customerId);
            ResultSet res = state.executeQuery();
            ArrayList<Shipping> arr = new ArrayList<>();
            while (res.next()) {
                arr.add(new Shipping(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("phone"),
                        res.getString("address"),
                        res.getString("mailCode"),
                        res.getInt("userId")
                ));
            }
            return arr.toArray(new Shipping[0]);
        } catch (SQLException e) {
            System.err.printf("Error while getting shipping (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static Shipping getShippingById(Database database, int id) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT * FROM `" + database.conf.DATABASE_TABLE_SHIPPING + "` WHERE id=?"
            );
            state.setInt(1, id);
            ResultSet res = state.executeQuery();
            if (!res.next()) return null;
            return new Shipping(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("phone"),
                        res.getString("address"),
                        res.getString("mailCode"),
                        res.getInt("userId")
            );
        } catch (SQLException e) {
            System.err.printf("Error while getting shipping (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }
}
