package top.chorg.icommerce.bean.model;

public class CustomerShippingInfoModel {

    private final int ship_id;
    private final int c_id;
    private final String ship_content;

    public CustomerShippingInfoModel(int ship_id, int c_id, String ship_content) {
        this.ship_id = ship_id;
        this.c_id = c_id;
        this.ship_content = ship_content;
    }
}
