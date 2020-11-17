package top.chorg.icommerce.bean.dto;

public class FileUploadResult {

    private final String fileCode;

    public FileUploadResult(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileCode() {
        return fileCode;
    }

    @Override
    public String toString() {
        return "FileUploadResult{" +
                "fileCode='" + fileCode + '\'' +
                '}';
    }
}
