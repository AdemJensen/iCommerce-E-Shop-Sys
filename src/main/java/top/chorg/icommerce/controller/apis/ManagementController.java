package top.chorg.icommerce.controller.apis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.chorg.icommerce.common.GeneralContext;
import top.chorg.icommerce.service.FileService;

@Controller
@RequestMapping(value = "/admin/actions")
public class ManagementController {

    final GeneralContext generalContext;

    private final FileService fileService;

    public ManagementController(GeneralContext generalContext, FileService fileService) {
        this.generalContext = generalContext;
        this.fileService = fileService;
    }

    @RequestMapping(value = "edit/index")
    public String editIndex(Model model) {
        model.addAttribute("general", generalContext);
        return "admin/dashboard";
    }

}
