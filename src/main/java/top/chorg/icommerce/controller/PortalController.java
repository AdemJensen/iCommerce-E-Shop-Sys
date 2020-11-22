package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.chorg.icommerce.common.GeneralContext;

@Controller
@RequestMapping(value = "/")
public class PortalController {

    final GeneralContext generalContext;

    public PortalController(GeneralContext generalContext) {
        this.generalContext = generalContext;
    }

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("general", generalContext);
        return "portal/index";
    }

}
