package top.chorg.icommerce.dao;

public interface FileDao {

    String getFileName(String id);

    String generateFileId(String filename, int uploader);

}
