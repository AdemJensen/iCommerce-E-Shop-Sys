package top.chorg.icommerce.bean.dto;

public class IndexItem {

    private final int index;
    private final String coverId;
    private final String title;
    private final String url;

    public IndexItem(int index, String coverId, String title, String url) {
        this.index = index;
        this.coverId = coverId;
        this.title = title;
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public String getCoverId() {
        return coverId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
