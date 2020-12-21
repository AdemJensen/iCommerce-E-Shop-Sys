package top.chorg.icommerce.bean.model;

public class PageModel {

    private final int p_id;
    private final String p_name;
    private final String p_cover;
    private final String p_content;
    private final String p_usage;

    public PageModel(int p_id, String p_name, String p_cover, String p_content, String p_usage) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.p_cover = p_cover;
        this.p_content = p_content;
        this.p_usage = p_usage;
    }
}
