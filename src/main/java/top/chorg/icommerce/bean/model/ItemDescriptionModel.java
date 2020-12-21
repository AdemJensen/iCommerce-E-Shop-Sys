package top.chorg.icommerce.bean.model;

public class ItemDescriptionModel {

    private final int i_id;
    private final int i_desc_version;
    private final String i_desc_content;

    public ItemDescriptionModel(int i_id, int i_desc_version, String i_desc_content) {
        this.i_id = i_id;
        this.i_desc_version = i_desc_version;
        this.i_desc_content = i_desc_content;
    }
}
