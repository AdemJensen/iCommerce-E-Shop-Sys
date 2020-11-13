package top.chorg.icommerce.dao;

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

}
