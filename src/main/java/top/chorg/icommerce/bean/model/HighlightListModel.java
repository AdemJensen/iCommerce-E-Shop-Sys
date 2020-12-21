package top.chorg.icommerce.bean.model;

public class HighlightListModel {

    private final int h_id;
    private final String h_name;
    private final String h_cover;
    private final String h_description;

    public HighlightListModel(int h_id, String h_name, String h_cover, String h_description) {
        this.h_id = h_id;
        this.h_name = h_name;
        this.h_cover = h_cover;
        this.h_description = h_description;
    }
}
