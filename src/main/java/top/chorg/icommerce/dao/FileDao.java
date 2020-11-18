package top.chorg.icommerce.dao;

import top.chorg.icommerce.bean.model.FileListItem;

import java.util.List;

public interface FileDao {

    /**
     * Get the original upload name of the file.
     *
     * @param code File Access code.
     * @return The file name.
     */
    String getFileName(String code);

    /**
     * Insert a record into the file DB.
     * Note that the file is not 100% valid, just the record.
     *
     * @param filename The original filename.
     * @param uploader Admin who uploaded this file.
     * @return The file access code.
     */
    String insertFileRecord(String filename, int uploader);


    /**
     * Get the uploaded file list.
     *
     * @param pageLength The length of page.
     * @param page Page number. Starts from 0.
     * @return The file list.
     */
    List<FileListItem> getFileList(int pageLength, int page);

    /**
     * Get the count of uploaded file list.
     *
     * @return The count.
     */
    int getFileNumber();

}
