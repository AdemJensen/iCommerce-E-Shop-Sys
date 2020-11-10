package top.chorg.icommerce.common;

import org.springframework.stereotype.Component;
import top.chorg.icommerce.config.SiteSettings;

// 本类用于存储所有页面都需要的 context
@Component
public class GeneralContext {

    private final SiteSettings siteSettings;

    public GeneralContext(SiteSettings siteSettings) {
        this.siteSettings = siteSettings;
    }

    public SiteSettings getSiteSettings() {
        return siteSettings;
    }

}
