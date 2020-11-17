package top.chorg.icommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/configurations.properties")
@ConfigurationProperties(prefix = "storage")
public class StorageSettings {

    private String basePath;

    private String uploadPath;
    private String avatarsPath;

    // Getter 必须有，否则 Thymeleaf 里面无法获取
    public String getBasePath() {
        return basePath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getAvatarsPath() {
        return avatarsPath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public void setAvatarsPath(String avatarsPath) {
        this.avatarsPath = avatarsPath;
    }
}
