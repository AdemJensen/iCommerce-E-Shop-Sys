package top.chorg.icommerce.bean.model;

import java.util.Date;

public class PreorderModel {

    private final int po_id;
    private final int c_id;
    private final Date po_create_time;
    private final double po_advance_final_price;
    private final double po_end_final_price;
    private final String po_shipping_info;
    private final String po_status;
    private final String po_refund_status;
    private final boolean po_is_hidden;

    public PreorderModel(int po_id, int c_id, Date po_create_time, double po_advance_final_price,
                         double po_end_final_price, String po_shipping_info, String po_status,
                         String po_refund_status, boolean po_is_hidden) {
        this.po_id = po_id;
        this.c_id = c_id;
        this.po_create_time = po_create_time;
        this.po_advance_final_price = po_advance_final_price;
        this.po_end_final_price = po_end_final_price;
        this.po_shipping_info = po_shipping_info;
        this.po_status = po_status;
        this.po_refund_status = po_refund_status;
        this.po_is_hidden = po_is_hidden;
    }
}
