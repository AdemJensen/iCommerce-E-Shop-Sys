package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.chorg.icommerce.common.GeneralContext;
import top.chorg.icommerce.service.FileService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    final GeneralContext generalContext;

    private final FileService fileService;

    public AdminController(GeneralContext generalContext, FileService fileService) {
        this.generalContext = generalContext;
        this.fileService = fileService;
    }

    @RequestMapping(value = "dashboard")
    public String dashboard(Model model) {
        model.addAttribute("general", generalContext);
        return "admin/dashboard";
    }

    @RequestMapping(value = "file")
    public String file(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        model.addAttribute("general", generalContext);
        return fileService.getFileList(model, page);
    }

}
