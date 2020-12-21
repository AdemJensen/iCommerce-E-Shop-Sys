package top.chorg.icommerce.bean.model;

public class ItemCommentModel {

    private final int i_id;
    private final String c_id;
    private final String co_id;
    private final double com_rate;
    private final String com_content;
    private final boolean com_autonomous;

    public ItemCommentModel(int i_id, String c_id, String co_id, double com_rate, String com_content, boolean com_autonomous) {
        this.i_id = i_id;
        this.c_id = c_id;
        this.co_id = co_id;
        this.com_rate = com_rate;
        this.com_content = com_content;
        this.com_autonomous = com_autonomous;
    }
}
