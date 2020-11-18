package top.chorg.icommerce.bean.model;

import java.util.Date;

public class FileListItem {

    private final String code;
    private final String filename;
    private final Date uploadTime;
    private final String uploaderDisplayableName;

    public FileListItem(String code, String filename, Date uploadTime, String uploaderDisplayableName) {
        this.code = code;
        this.filename = filename;
        this.uploadTime = uploadTime;
        this.uploaderDisplayableName = uploaderDisplayableName;
    }

    public String getCode() {
        return code;
    }

    public String getFilename() {
        return filename;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public String getUploaderDisplayableName() {
        return uploaderDisplayableName;
    }
}
