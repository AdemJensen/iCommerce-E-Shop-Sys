package top.chorg.icommerce.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.chorg.icommerce.common.utils.MD5;
import top.chorg.icommerce.common.utils.RandomStrings;
import top.chorg.icommerce.dao.FileDao;

@Repository
public class FileDaoImpl implements FileDao {

    @Autowired
    private JdbcTemplate dbTemplate;

    @Override
    public String getFileName(String id) throws DataAccessException {
        return dbTemplate.queryForObject(
                "SELECT filename FROM files WHERE fileId=?",
                new Object[] {id},
                String.class
        );
    }

    @Override
    public String generateFileId(String filename, int uploader) {
        String fileId = MD5.encode(filename + uploader + RandomStrings.getRandomString(40));
        dbTemplate.update(
                "INSERT INTO files (fileId, filename, uploader) VALUES (?, ?, ?)",
                fileId, filename, uploader
        );
        return fileId;
    }
}
