package top.chorg.icommerce.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.chorg.icommerce.bean.dto.Admin;
import top.chorg.icommerce.bean.dto.AdminType;
import top.chorg.icommerce.common.AuthSession;
import top.chorg.icommerce.dao.AuthDao;
import top.chorg.icommerce.service.AuthService;

import javax.servlet.http.HttpSession;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthDao authDao;
    private final AuthSession authSession;

    public AuthServiceImpl(AuthDao authDao, AuthSession authSession) {
        this.authDao = authDao;
        this.authSession = authSession;
    }


    @Override
    public int authenticateCustomer(String username, String password) {
        return 0;
    }

    @Override
    public int authenticateAdmin(HttpSession session, String username, String password) {
        int id = authDao.authenticateAdmin(username, password);
        if (id < 0) return id;
        if (!authSession.createAuthSessionForAdmin(session, id)) return -1;
        return id;
    }



    @Override
    public void debugService() {
        LOG.debug("Executing initial test sequence...");
        authDao.addAdmin(
                "rentenglong@163.com",
                "root", "e10adc3949ba59abbe56e057f20f883e",
                AdminType.RootAdmin
        );
        LOG.debug("Test complete.");
    }
}
