package top.chorg.iCommerce.database;

import top.chorg.iCommerce.api.CartItem;
import top.chorg.iCommerce.api.Item;
import top.chorg.iCommerce.api.ItemType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemData {

    public static ItemType getTypeById(Database database, int id) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT * FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE + "` WHERE id=?"
            );
            state.setInt(1, id);
            ResultSet res = state.executeQuery();
            if (!res.next()) return null;
            return new ItemType(
                    res.getInt("id"),
                    res.getString("type_name"),
                    res.getString("type_comment"),
                    res.getInt("cover")
            );
        } catch (SQLException e) {
            System.err.printf("Error while getting type (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static ItemType[] getTypeList(Database database) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT * FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE + "`"
            );
            return handleItemTypes(state);
        } catch (SQLException e) {
            System.err.printf("Error while getting TypeList (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static int addItemType(Database database, String name, int cover, String comment) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "INSERT INTO `" + database.conf.DATABASE_TABLE_ITEM_TYPE + "` (type_name, cover, type_comment) VALUES (?, ?, ?)"
            );
            state.setString(1, name);
            state.setInt(2, cover);
            state.setString(3, comment);
            int res = state.executeUpdate();
            if (res != 1) return 1; // 数据库未受到影响
            return 0;   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while adding ItemType (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int editItemType(Database database, int id, String name, int cover, String comment) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "UPDATE `" + database.conf.DATABASE_TABLE_ITEM_TYPE + "` SET type_name=?, cover=?, type_comment=? WHERE id=?"
            );
            state.setString(1, name);
            state.setInt(2, cover);
            state.setString(3, comment);
            state.setInt(4, id);
            int res = state.executeUpdate();
            if (res != 1) return 1; // 数据库未受到影响
            return 0;   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while editing ItemType (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int removeItemType(Database database, int id) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "DELETE FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE + "` WHERE id=?"
            );
            state.setInt(1, id);
            int res = state.executeUpdate();
            if (res != 1) return 1; // 数据库未受到影响
            state = database.database.prepareStatement(
                    "DELETE FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` WHERE typeId=?"
            );
            state.setInt(1, id);
            state.executeUpdate();
            return 0;   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while removing ItemType (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int ITEM_ORDER_NONE = 0;
    public static int ITEM_ORDER_D_HTL = 1;
    public static int ITEM_ORDER_D_LTH = 2;
    public static int ITEM_ORDER_P_HTL = 3;
    public static int ITEM_ORDER_P_LTH = 4;
    public static int ITEM_ORDER_T_DESC = 5;
    public static int ITEM_ORDER_T = 6;
    public static int ITEM_ORDER_DISCOUNT = 7;

    public static int getItemListLength(Database database, int type) {
        try {
            PreparedStatement state;
            if (type == 0) {
                state = database.database.prepareStatement(
                        "SELECT COUNT(*) cnt FROM `" + database.conf.DATABASE_TABLE_ITEM + "` "
                );
            } else {
                state = database.database.prepareStatement(
                        "SELECT COUNT(*) cnt FROM `" + database.conf.DATABASE_TABLE_ITEM + "` WHERE id IN (SELECT itemId FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` WHERE typeId=?) "
                );
                state.setInt(1, type);
            }
            ResultSet res = state.executeQuery();
            if (!res.next()) return -1;
            return res.getInt("cnt");
        } catch (SQLException e) {
            System.err.printf("Error while getting ItemListLen (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int getItemQuantity(Database database, int itemId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT quantity FROM `" + database.conf.DATABASE_TABLE_ITEM + "` WHERE id=?"
            );
            state.setDouble(1, itemId);
            ResultSet res = state.executeQuery();
            if (res.next()) return res.getInt("quantity");
            else return 0;
        } catch (SQLException e) {
            System.err.printf("Error while getting quantity (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int getItemDeals(Database database, int itemId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT deals FROM `" + database.conf.DATABASE_TABLE_ITEM + "` WHERE id=?"
            );
            state.setDouble(1, itemId);
            ResultSet res = state.executeQuery();
            if (res.next()) return res.getInt("deals");
            else return 0;
        } catch (SQLException e) {
            System.err.printf("Error while getting deals (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int subQuantity(Database database, int itemId, int quantity) {
        try {
            int iq = getItemQuantity(database, itemId);
            if (iq < quantity) return 1;
            PreparedStatement state = database.database.prepareStatement(
                    "UPDATE `" + database.conf.DATABASE_TABLE_ITEM + "` SET quantity=? WHERE id=?"
            );
            state.setDouble(1, iq - quantity);
            state.setInt(2, itemId);
            int res = state.executeUpdate();
            return res > 0 ? 0 : 2;
        } catch (SQLException e) {
            System.err.printf("Error while subtracting quantity (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int addDeals(Database database, int itemId, int deal) {
        try {
            int iq = getItemDeals(database, itemId);
            PreparedStatement state = database.database.prepareStatement(
                    "UPDATE `" + database.conf.DATABASE_TABLE_ITEM + "` SET deals=? WHERE id=?"
            );
            state.setDouble(1, iq + deal);
            state.setInt(2, itemId);
            int res = state.executeUpdate();
            return res > 0 ? 0 : 1;
        } catch (SQLException e) {
            System.err.printf("Error while adding deal (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static Item getItemById(Database database, int id) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT * FROM `" + database.conf.DATABASE_TABLE_ITEM + "` WHERE id=?"
            );
            state.setInt(1, id);
            ResultSet res = state.executeQuery();
            if (!res.next()) return null;
            return new Item(
                    res.getInt("id"),
                    res.getString("name"),
                    res.getString("detail_page"),
                    res.getDouble("price_old"),
                    res.getDouble("price_new"),
                    res.getInt("cover"),
                    res.getInt("quantity"),
                    res.getInt("deals"),
                    res.getTimestamp("releaseDate")
            );
        } catch (SQLException e) {
            System.err.printf("Error while getting item (%s)\n", e.getMessage());
            return null;
        }

    }

    public static Item[] getItemList(Database database, int type, int order) {
        return getItemList(database, 0, 0, type, order);
    }

    public static Item[] getItemList(Database database, int page, int pageSize, int type, int order) {
        switch (order) {
            case 1:
                return getItemList(database, type, page, pageSize, "ORDER BY deals DESC ");
            case 2:
                return getItemList(database, type, page, pageSize, "ORDER BY deals ");
            case 3:
                return getItemList(database, type, page, pageSize, "ORDER BY price_new DESC ");
            case 4:
                return getItemList(database, type, page, pageSize, "ORDER BY price_new ");
            case 5:
                return getItemList(database, type, page, pageSize, "ORDER BY releaseDate DESC ");
            case 6:
                return getItemList(database, type, page, pageSize, "ORDER BY releaseDate ");
            case 7:
                return getItemList(database, type, page, pageSize, "ORDER BY price_new / price_old ");
            default:
                return getItemList(database, type, page, pageSize, " ");
        }
    }

    public static ItemType[] getType(Database database, int item) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT * FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE + "` WHERE id IN (SELECT typeId FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` WHERE itemId=?)"
            );
            state.setInt(1, item);
            return handleItemTypes(state);
        } catch (SQLException e) {
            System.err.printf("Error while getting Type (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    private static ItemType[] handleItemTypes(PreparedStatement state) throws SQLException {
        ResultSet res = state.executeQuery();
        ArrayList<ItemType> result = new ArrayList<>();
        for (int i = 0; res.next(); i++) {
            result.add(new ItemType(
                    res.getInt("id"),
                    res.getString("type_name"),
                    res.getString("type_comment"),
                    res.getInt("cover")
            ));
        }
        return result.toArray(new ItemType[0]);   // 成功
    }

    private static Item[] getItemList(Database database, int type, int page, int pageSize, String order) {
        try {
            String limit;
            if (pageSize <= 0 || page <= 0) limit = "";
            else limit = String.format("limit %d, %d ", (page - 1) * pageSize, pageSize);
            PreparedStatement state;
            if (type == 0) {
                state = database.database.prepareStatement(
                        "SELECT * FROM `" + database.conf.DATABASE_TABLE_ITEM + "` " + order + limit
                );
            } else {
                state = database.database.prepareStatement(
                        "SELECT * FROM `" + database.conf.DATABASE_TABLE_ITEM + "` WHERE id IN (SELECT itemId FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` WHERE typeId=?) " + order + limit
                );
                state.setInt(1, type);
            }
            ResultSet res = state.executeQuery();
            ArrayList<Item> result = new ArrayList<>();
            for (int i = 0; res.next(); i++) {
                result.add(new Item(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("detail_page"),
                        res.getDouble("price_old"),
                        res.getDouble("price_new"),
                        res.getInt("cover"),
                        res.getInt("quantity"),
                        res.getInt("deals"),
                        res.getTimestamp("releaseDate")
                ));
            }
            return result.toArray(new Item[0]);   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while getting ItemList (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static int addItem(Database database, String name, int cover, String detailPage, double oldPrice, double newPrice, int quantity, int[] types) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "INSERT INTO `" + database.conf.DATABASE_TABLE_ITEM + "` (name, cover, detail_page, price_old, price_new, quantity) VALUES (?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            state.setString(1, name);
            state.setInt(2, cover);
            state.setString(3, detailPage);
            state.setDouble(4, oldPrice);
            state.setDouble(5, newPrice);
            state.setInt(6, quantity);
            int res = state.executeUpdate();
            if (res != 1) return 1; // 数据库未受到影响
            ResultSet set = state.getGeneratedKeys();
            set.next();
            int id = set.getInt(1);
            state = database.database.prepareStatement(
                    "INSERT INTO `" + database.conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` VALUES (?, ?)"
            );
            state.setInt(1, id);
            for (int type : types) {
                state.setInt(2, type);
                state.executeUpdate();
            }
            return 0;   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while adding Item (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int editItem(Database database, int id, String name, int cover, String detailPage, double oldPrice, double newPrice, int quantity, int[] types) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "UPDATE `" + database.conf.DATABASE_TABLE_ITEM + "` SET name=?, cover=?, detail_page=?, price_old=?, price_new=?, quantity=? WHERE id=?"
            );
            state.setString(1, name);
            state.setInt(2, cover);
            state.setString(3, detailPage);
            state.setDouble(4, oldPrice);
            state.setDouble(5, newPrice);
            state.setInt(6, quantity);
            state.setInt(7, id);
            int res = state.executeUpdate();
            if (res != 1) return 1; // 数据库未受到影响
            state = database.database.prepareStatement(
                    "DELETE FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` WHERE itemId=?"
            );
            state.setInt(1, id);
            state.executeUpdate();
            state = database.database.prepareStatement(
                    "INSERT INTO `" + database.conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` VALUES (?, ?)"
            );
            state.setInt(1, id);
            for (int type : types) {
                state.setInt(2, type);
                state.executeUpdate();
            }
            return 0;   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while editing Item (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int removeItem(Database database, int id) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "DELETE FROM `" + database.conf.DATABASE_TABLE_ITEM + "` WHERE id=?"
            );
            state.setInt(1, id);
            int res = state.executeUpdate();
            if (res != 1) return 1; // 数据库未受到影响
            state = database.database.prepareStatement(
                    "DELETE FROM `" + database.conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` WHERE itemId=?"
            );
            state.setInt(1, id);
            state.executeUpdate();
            return 0;   // 成功
        } catch (SQLException e) {
            System.err.printf("Error while removing Item (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static CartItem[] getCart(Database database, int customerId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SELECT `" + database.conf.DATABASE_TABLE_ITEM + "`.id, `" + database.conf.DATABASE_TABLE_ITEM + "`.name, `" + database.conf.DATABASE_TABLE_ITEM + "`.detail_page, `" + database.conf.DATABASE_TABLE_ITEM + "`.price_old, `" + database.conf.DATABASE_TABLE_ITEM + "`.price_new, " +
                            "`" + database.conf.DATABASE_TABLE_ITEM + "`.cover, `" + database.conf.DATABASE_TABLE_ITEM + "`.quantity, `" + database.conf.DATABASE_TABLE_ITEM + "`.deals, `" + database.conf.DATABASE_TABLE_ITEM + "`.releasedate, `" + database.conf.DATABASE_TABLE_CART + "`.quantity " +
                            "FROM `" + database.conf.DATABASE_TABLE_ITEM + "`, `" + database.conf.DATABASE_TABLE_CART + "` WHERE `" + database.conf.DATABASE_TABLE_CART + "`.userId=? AND `" + database.conf.DATABASE_TABLE_ITEM + "`.id=`" + database.conf.DATABASE_TABLE_CART + "`.itemId;"
            );
            state.setInt(1, customerId);
            ResultSet res = state.executeQuery();
            ArrayList<CartItem> list = new ArrayList<>();
            while (res.next()) {
                list.add(new CartItem(
                        new Item(
                                res.getInt("id"),
                                res.getString("name"),
                                res.getString("detail_page"),
                                res.getDouble("price_old"),
                                res.getDouble("price_new"),
                                res.getInt("cover"),
                                res.getInt(database.conf.DATABASE_TABLE_ITEM + ".quantity"),
                                res.getInt("deals"),
                                res.getTimestamp("releaseDate")
                        ),
                        res.getInt(database.conf.DATABASE_TABLE_CART + ".quantity")
                ));
            }
            return list.toArray(new CartItem[0]);
        } catch (SQLException e) {
            System.err.printf("Error while getting cart (%s)\n", e.getMessage());
            return null;  // 出现未知错误
        }
    }

    public static int addCartItem(Database database, int customerId, int itemId, int quantity) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "SElECT * FROM `" + database.conf.DATABASE_TABLE_CART + "` WHERE userId=? AND itemId=?"
            );
            state.setInt(1, customerId);
            state.setInt(2, itemId);
            ResultSet res = state.executeQuery();
            if (res.next()) {
                int oriQuantity = res.getInt("quantity");
                state = database.database.prepareStatement(
                        "UPDATE `" + database.conf.DATABASE_TABLE_CART + "` SET quantity=? WHERE userId=? AND itemId=?"
                );
                state.setInt(1, oriQuantity + quantity);
                state.setInt(2, customerId);
                state.setInt(3, itemId);
            } else {
                state = database.database.prepareStatement(
                        "INSERT INTO `" + database.conf.DATABASE_TABLE_CART + "` VALUES (?, ?, ?)"
                );
                state.setInt(1, customerId);
                state.setInt(2, itemId);
                state.setInt(3, quantity);
            }
            return state.executeUpdate() > 0 ? 0 : 1; // 0 = SUCCESS, 1 = FAIL
        } catch (SQLException e) {
            System.err.printf("Error while adding cart item (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int setCartItem(Database database, int customerId, int itemId, int quantity) {
        try {
            PreparedStatement state;
            if (quantity > 0) {
                state = database.database.prepareStatement(
                        "SElECT * FROM `" + database.conf.DATABASE_TABLE_CART + "` WHERE userId=? AND itemId=?"
                );
                state.setInt(1, customerId);
                state.setInt(2, itemId);
                ResultSet res = state.executeQuery();
                if (res.next()) {
                    state = database.database.prepareStatement(
                            "UPDATE `" + database.conf.DATABASE_TABLE_CART + "` SET quantity=? WHERE userId=? AND itemId=?"
                    );
                    state.setInt(1, quantity);
                    state.setInt(2, customerId);
                    state.setInt(3, itemId);
                } else {
                    state = database.database.prepareStatement(
                            "INSERT INTO `" + database.conf.DATABASE_TABLE_CART + "` VALUES (?, ?, ?)"
                    );
                    state.setInt(1, customerId);
                    state.setInt(2, itemId);
                    state.setInt(3, quantity);
                }
                return state.executeUpdate() > 0 ? 0 : 1; // 0 = SUCCESS, 1 = FAIL
            } else {
                state = database.database.prepareStatement(
                        "DELETE FROM `" + database.conf.DATABASE_TABLE_CART + "` WHERE userId=? AND itemId=?"
                );
                state.setInt(1, customerId);
                state.setInt(2, itemId);
                state.executeUpdate();
                return 0;
            }
        } catch (SQLException e) {
            System.err.printf("Error while setting cart item (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

    public static int clearCart(Database database, int customerId) {
        try {
            PreparedStatement state = database.database.prepareStatement(
                    "DELETE FROM `" + database.conf.DATABASE_TABLE_CART + "` WHERE userId=?"
            );
            state.setInt(1, customerId);
            int res = state.executeUpdate();
            return 0;
        } catch (SQLException e) {
            System.err.printf("Error while getting cart (%s)\n", e.getMessage());
            return -1;  // 出现未知错误
        }
    }

}
