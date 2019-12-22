package top.chorg.iCommerce.api;

import java.util.Date;

public class Order {
    public int id;
    public String orderNum;
    public Date orderTime;
    public int userId;
    public int shippingId;
    public int status;
    public String expressNum;

    public Order(int id, String orderNum, Date orderTime, int userId, int shippingId, int status, String expressNum) {
        this.id = id;
        this.orderNum = orderNum;
        this.orderTime = orderTime;
        this.userId = userId;
        this.shippingId = shippingId;
        this.status = status;
        this.expressNum = expressNum;
    }
}
