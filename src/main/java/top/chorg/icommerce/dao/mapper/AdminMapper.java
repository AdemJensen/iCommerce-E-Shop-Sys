package top.chorg.icommerce.dao.mapper;

import top.chorg.icommerce.bean.model.AdminModel;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper implements RowMapper<AdminModel> {

    @Override
    public AdminModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AdminModel(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getTimestamp(6)
        );
    }
}
