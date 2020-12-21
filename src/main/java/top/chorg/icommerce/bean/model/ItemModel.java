package top.chorg.icommerce.bean.model;

import java.util.Date;

public class ItemModel {
    private final int i_id;
    private final String i_name;
    private final String i_cover;
    private final double i_real_price;
    private final double i_fake_price;
    private final Date i_release_date;
    private final double i_refund_rate;
    private final int i_inventory;
    private final String i_status;

    public ItemModel(int i_id, String i_name, String i_cover, double i_real_price, double i_fake_price,
                     Date i_release_date, double i_refund_rate, int i_inventory, String i_status) {
        this.i_id = i_id;
        this.i_name = i_name;
        this.i_cover = i_cover;
        this.i_real_price = i_real_price;
        this.i_fake_price = i_fake_price;
        this.i_release_date = i_release_date;
        this.i_refund_rate = i_refund_rate;
        this.i_inventory = i_inventory;
        this.i_status = i_status;
    }
}
