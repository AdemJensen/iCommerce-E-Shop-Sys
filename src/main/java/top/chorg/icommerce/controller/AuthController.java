package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.chorg.icommerce.common.GeneralContext;

@Controller
@RequestMapping(value = "/customer")
public class AuthController {

    final GeneralContext generalContext;

    public AuthController(GeneralContext generalContext) {
        this.generalContext = generalContext;
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

}
