package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.chorg.icommerce.common.GeneralContext;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    final GeneralContext generalContext;

    public AdminController(GeneralContext generalContext) {
        this.generalContext = generalContext;
    }

    @RequestMapping(value = "dashboard")
    public String dashboard(Model model) {
        model.addAttribute("general", generalContext);
        return "admin/dashboard";
    }

    @RequestMapping(value = "file")
    public String file(Model model) {
        model.addAttribute("general", generalContext);
        return "admin/fileManage";
    }

}
