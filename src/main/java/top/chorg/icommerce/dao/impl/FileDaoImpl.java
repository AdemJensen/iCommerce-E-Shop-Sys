package top.chorg.icommerce.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.chorg.icommerce.common.StringRules;
import top.chorg.icommerce.dao.FileDao;
import top.chorg.icommerce.service.impl.FileServiceImpl;

@Repository
public class FileDaoImpl implements FileDao {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    private final JdbcTemplate dbTemplate;

    public FileDaoImpl(JdbcTemplate dbTemplate) {
        this.dbTemplate = dbTemplate;
    }

    @Override
    public String getFileName(String code) {
        try {
            return dbTemplate.queryForObject(
                    "SELECT f_name FROM files WHERE f_code=?",
                    new Object[] {code},
                    String.class
            );
        } catch (DataAccessException e) {
            LOG.debug("Got `{}` exception when getFileName, param is (\"{}\")", e.toString(), code);
            return null;
        }
    }

    @Override
    public String insertFileRecord(String filename, int uploader) {
        String fileCode = StringRules.getAdminUploadFilename(filename, uploader);
        try {
            dbTemplate.update(
                    "INSERT INTO files (f_code, f_name, uploader) VALUES (?, ?, ?)",
                    fileCode, filename, uploader
            );
            return fileCode;
        } catch (DataAccessException e) {
            LOG.debug("Got `{}` exception when insertFileRecord, param is (\"{}\", \"{}\"), " +
                    "generated code is \"{}\"", e.toString(), filename, uploader, fileCode);
            return null;
        }

    }
}
