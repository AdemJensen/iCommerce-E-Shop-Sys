package top.chorg.icommerce.bean.model;

public class CouponModel {

    private final int co_type_id;
    private final String co_name;
    private final String co_description;
    private final String co_rules;

    public CouponModel(int co_type_id, String co_name, String co_description, String co_rules) {
        this.co_type_id = co_type_id;
        this.co_name = co_name;
        this.co_description = co_description;
        this.co_rules = co_rules;
    }
}
