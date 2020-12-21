package top.chorg.icommerce.bean.model;

import java.util.Date;

public class PreorderItemModel {

    private final int i_id;
    private final int po_id;
    private final int i_desc_version;
    private final String i_snap_name;
    private final double i_snap_real_price;
    private final double i_snap_fake_price;
    private final Date i_snap_release_date;
    private final double i_snap_refund_rate_end;

    public PreorderItemModel(int i_id, int po_id, int i_desc_version, String i_snap_name, double i_snap_real_price,
                             double i_snap_fake_price, Date i_snap_release_date, double i_snap_refund_rate_end) {
        this.i_id = i_id;
        this.po_id = po_id;
        this.i_desc_version = i_desc_version;
        this.i_snap_name = i_snap_name;
        this.i_snap_real_price = i_snap_real_price;
        this.i_snap_fake_price = i_snap_fake_price;
        this.i_snap_release_date = i_snap_release_date;
        this.i_snap_refund_rate_end = i_snap_refund_rate_end;
    }
}
