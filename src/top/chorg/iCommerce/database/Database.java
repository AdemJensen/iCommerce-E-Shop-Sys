package top.chorg.iCommerce.database;

import top.chorg.iCommerce.api.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Connection database;
    public Config conf;

    public Database(Connection database, Config conf) {
        this.database = database;
        this.conf = conf;
    }

    public static Database connect(Config config) throws SQLException, ClassNotFoundException {
        return new Database(connect(config.DATABASE_ADDR, config.DATABASE_PORT, config.DATABASE_SCHEMA,
                config.DATABASE_USE_UNICODE, config.DATABASE_ENCODING,
                config.DATABASE_TIMEZONE, config.DATABASE_USE_SSL,
                config.PUBLIC_KEY_RETRIEVAL, config.DATABASE_USERNAME,
                config.DATABASE_PASSWORD
        ), config);
    }

    private static Connection connect(
            String addr, int port, String schema, boolean useUnicode, String encoding, String timezone,
            boolean useSSL, boolean allowPublicKeyRetrieval, String username, String password
    ) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://" + addr + ":" + port + ((timezone != null && schema.length() > 0) ? "/" : "") +
                        schema + "?useUnicode=" + (useUnicode ? "true" : "false") +
                        "&characterEncoding=" + encoding +
                        ((timezone != null && timezone.length() > 0) ? "&serverTimezone=" + timezone : "") +
                        "&useSSL="  + (useSSL ? "true" : "false")
                        + "&allowPublicKeyRetrieval="  + (allowPublicKeyRetrieval ? "true" : "false"),
                username, password
        );
    }

}
