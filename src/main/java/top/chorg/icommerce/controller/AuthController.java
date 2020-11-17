package top.chorg.icommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import top.chorg.icommerce.common.GeneralContext;
import top.chorg.icommerce.common.utils.NavigationUtils;
import top.chorg.icommerce.service.AuthService;
import top.chorg.icommerce.service.impl.AuthServiceImpl;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    final GeneralContext generalContext;
    final CommonsController commonsController;
    final AuthService authService;

    public AuthController(GeneralContext generalContext, CommonsController commonsController, AuthService authService) {
        this.generalContext = generalContext;
        this.commonsController = commonsController;
        this.authService = authService;
    }

    @RequestMapping(value = "login")
    public String customerLogin(Model model) {
        model.addAttribute("general", generalContext);
        return "auth/customerLogin";
    }

    @RequestMapping(value = "register")
    public String customerRegister(Model model) {
        model.addAttribute("general", generalContext);
        return "auth/customerRegister";
    }

    @RequestMapping(value = "exit")
    public String exitUser(Model model, HttpSession session) {
        model.addAttribute("general", generalContext);
        int res = authService.exitUser(session);
        if (res == -1) {
            return commonsController.info(
                    model, "错误", "danger", "错误",
                    "用户未登录", "-1", NavigationUtils.getLastVis(session)
            );
        }
        return commonsController.info(
                model, "登出成功", "success", "登出成功",
                "用户已成功登出", "-1", NavigationUtils.getLastVis(session)
        );
    }

    @RequestMapping(value = "admin/login")
    public String adminLogin(Model model) {
        model.addAttribute("general", generalContext);
        return "auth/adminLogin";
    }

    @PostMapping(value = "admin/login/handle")
    public String handleAdminLogin(
            Model model, HttpSession session,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        int result = authService.authenticateAdmin(session, username, password);
        if (result == -1) return commonsController.info(
                model, "登录失败", "danger", "登录失败",
                "用户名或密码错误", "/admin/login", NavigationUtils.getLastVis(session)
        );
        return commonsController.info(
                model, "登录成功", "success", "登录成功",
                "登录成功，欢迎您，管理员！", "-1", NavigationUtils.getLastVis(session)
        );
    }

}
