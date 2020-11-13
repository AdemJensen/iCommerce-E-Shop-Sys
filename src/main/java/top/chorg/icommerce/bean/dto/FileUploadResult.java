package top.chorg.icommerce.bean.dto;

public class FileUploadResult {

    private final String fileCode;

    public FileUploadResult(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileCode() {
        return fileCode;
    }

}
