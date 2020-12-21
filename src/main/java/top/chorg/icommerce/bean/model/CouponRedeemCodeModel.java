package top.chorg.icommerce.bean.model;

import java.util.Date;

public class CouponRedeemCodeModel {

    private final int co_type_id;
    private final String co_redeem_code;
    private final Date co_redeem_time;
    private final int c_id;

    public CouponRedeemCodeModel(int co_type_id, String co_redeem_code, Date co_redeem_time, int c_id) {
        this.co_type_id = co_type_id;
        this.co_redeem_code = co_redeem_code;
        this.co_redeem_time = co_redeem_time;
        this.c_id = c_id;
    }
}
