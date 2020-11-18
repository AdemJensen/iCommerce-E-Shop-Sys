package top.chorg.icommerce.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import top.chorg.icommerce.bean.model.FileListItem;
import top.chorg.icommerce.common.StringRules;
import top.chorg.icommerce.dao.FileDao;
import top.chorg.icommerce.dao.mapper.FileListItemMapper;
import top.chorg.icommerce.service.impl.FileServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    "INSERT INTO files (f_code, f_name, uploader, f_upload_time) VALUES (?, ?, ?, NOW())",
                    fileCode, filename, uploader
            );
            return fileCode;
        } catch (DataAccessException e) {
            LOG.debug("Got `{}` exception when insertFileRecord, param is (\"{}\", \"{}\"), " +
                    "generated code is \"{}\"", e.toString(), filename, uploader, fileCode);
            return null;
        }

    }

    @Override
    public List<FileListItem> getFileList(int pageLength, int page) {
        try {
            return dbTemplate.query(
                    "SELECT f_code, f_name, f_upload_time, " +
                            "(SELECT a_username FROM admins WHERE a_id=uploader) a_username, " +
                            "(SELECT a_nickname FROM admins WHERE a_id=uploader) a_nickname " +
                            "FROM files ORDER BY f_upload_time DESC LIMIT ?, ?",
                    new Object[] {page, pageLength},
                    new FileListItemMapper()
            );
        } catch (DataAccessException e) {
            LOG.debug("Got `{}` exception when getFileList, no param.", e.toString());
            return new ArrayList<>();
        }
    }

    @Override
    public int getFileNumber() {
        try {
            Integer count = dbTemplate.queryForObject(
                    "SELECT count(*) FROM files", Integer.class
            );
            if (count == null) throw new NullPointerException();
            return count;
        } catch (DataAccessException e) {
            LOG.debug("Got `{}` exception when getFileList, no param.", e.toString());
            return 0;
        }
    }


}
