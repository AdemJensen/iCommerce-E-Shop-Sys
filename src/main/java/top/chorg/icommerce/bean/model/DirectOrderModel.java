package top.chorg.icommerce.bean.model;

import java.util.Date;

public class DirectOrderModel {

    private final int do_id;
    private final int c_id;
    private final Date do_create_time;
    private final double do_final_price;
    private final String do_shipping_info;
    private final String do_status;
    private final boolean do_is_hidden;

    public DirectOrderModel(int do_id, int c_id, Date do_create_time, double do_final_price, String do_shipping_info,
                            String do_status, boolean do_is_hidden) {
        this.do_id = do_id;
        this.c_id = c_id;
        this.do_create_time = do_create_time;
        this.do_final_price = do_final_price;
        this.do_shipping_info = do_shipping_info;
        this.do_status = do_status;
        this.do_is_hidden = do_is_hidden;
    }
}
