package top.chorg.icommerce.bean.dto;

public class FileUploadResult {

    private String fileId;
    private String fileUrl;

    public FileUploadResult(String fileId, String fileUrl) {
        this.fileId = fileId;
        this.fileUrl = fileUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
