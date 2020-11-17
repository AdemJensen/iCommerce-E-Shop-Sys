package top.chorg.icommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/configurations.properties")
@ConfigurationProperties(prefix = "master")
public class MasterSettings {

    // @Value("${master.siteName}")
    private String siteName;

    // Getter 必须有，否则 Thymeleaf 里面无法获取
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

}
