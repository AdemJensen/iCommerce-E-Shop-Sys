package top.chorg.icommerce.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import top.chorg.icommerce.bean.model.AdminModel;
import top.chorg.icommerce.bean.model.FileListItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FileListItemMapper implements RowMapper<FileListItem> {

    @Override
    public FileListItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FileListItem(
                rs.getString(1),
                rs.getString(2),
                rs.getTimestamp(3),
                String.format("%s(%s)", rs.getString(4), rs.getString(5))
        );
    }
}
