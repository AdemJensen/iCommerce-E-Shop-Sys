package top.chorg.icommerce.bean.model;

import top.chorg.icommerce.bean.dto.IndexItem;

public class IndexItemModel {

    private final int disp_index;
    private final String disp_cover;
    private final String disp_title;
    private final String disp_url;

    public IndexItemModel(int disp_index, String disp_cover, String disp_title, String disp_url) {
        this.disp_index = disp_index;
        this.disp_cover = disp_cover;
        this.disp_title = disp_title;
        this.disp_url = disp_url;
    }

    public IndexItem getDto() {
        return new IndexItem(disp_index, disp_cover, disp_title, disp_url);
    }

}
