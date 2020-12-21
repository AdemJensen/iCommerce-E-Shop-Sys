package top.chorg.icommerce.bean.model;

import java.util.Date;

public class ItemPreorderInfoModel {

    private final int i_id;
    private final double preo_p_advance;  // 定金
    private final Date preo_end_time;
    private final Date preo_expected_time;

    public ItemPreorderInfoModel(int i_id, double preo_p_advance, Date preo_end_time, Date preo_expected_time) {
        this.i_id = i_id;
        this.preo_p_advance = preo_p_advance;
        this.preo_end_time = preo_end_time;
        this.preo_expected_time = preo_expected_time;
    }
}
