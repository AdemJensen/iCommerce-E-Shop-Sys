package top.chorg.icommerce.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import top.chorg.icommerce.bean.model.AdminModel;
import top.chorg.icommerce.bean.model.CustomerModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<CustomerModel> {

    @Override
    public CustomerModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CustomerModel(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getDate(6),
                rs.getString(7),
                rs.getTimestamp(8),
                rs.getString(9)
        );
    }
}
