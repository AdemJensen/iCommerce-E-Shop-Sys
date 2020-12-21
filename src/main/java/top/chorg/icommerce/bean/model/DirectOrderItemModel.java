package top.chorg.icommerce.bean.model;

import java.util.Date;

public class DirectOrderItemModel {

    private final int i_id;
    private final int do_id;
    private final int i_desc_version;
    private final int do_quantity;
    private final String i_snap_name;
    private final double i_snap_real_price;
    private final double i_snap_fake_price;
    private final Date i_snap_release_date;
    private final double i_snap_refund_rate;
    private final String do_refund_status;
    private final int do_refund_quantity;

    public DirectOrderItemModel(int i_id, int do_id, int i_desc_version, int do_quantity, String i_snap_name,
                                double i_snap_real_price, double i_snap_fake_price, Date i_snap_release_date,
                                double i_snap_refund_rate, String do_refund_status, int do_refund_quantity) {
        this.i_id = i_id;
        this.do_id = do_id;
        this.i_desc_version = i_desc_version;
        this.do_quantity = do_quantity;
        this.i_snap_name = i_snap_name;
        this.i_snap_real_price = i_snap_real_price;
        this.i_snap_fake_price = i_snap_fake_price;
        this.i_snap_release_date = i_snap_release_date;
        this.i_snap_refund_rate = i_snap_refund_rate;
        this.do_refund_status = do_refund_status;
        this.do_refund_quantity = do_refund_quantity;
    }
}
