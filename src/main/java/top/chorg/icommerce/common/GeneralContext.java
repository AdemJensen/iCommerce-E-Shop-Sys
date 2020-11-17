package top.chorg.icommerce.common;

import org.springframework.stereotype.Component;
import top.chorg.icommerce.config.MasterSettings;

import javax.servlet.http.HttpSession;

// 本类用于存储所有页面都需要的 context
@Component
public class GeneralContext {

    private final MasterSettings masterSettings;

    private final AuthSession authSession;

    private String currentUserType;

    public GeneralContext(MasterSettings masterSettings, AuthSession authSession) {
        this.masterSettings = masterSettings;
        this.authSession = authSession;
    }

    public MasterSettings getSiteSettings() {
        return masterSettings;
    }

    public AuthSession getAuthSession() {
        return authSession;
    }


    public String getCurrentUserType(HttpSession session) {
        if (session == null) return "null";
        return String.valueOf(authSession.getSessionUserType(session));
    }
}
