package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.chorg.icommerce.common.GeneralContext;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

    final GeneralContext generalContext;

    public ItemController(GeneralContext generalContext) {
        this.generalContext = generalContext;
    }



}
