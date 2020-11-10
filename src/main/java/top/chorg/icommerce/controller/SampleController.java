package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sample")
public class SampleController {

    @RequestMapping(value = "main")
    public String userLogin() {
        return "sample/sampleView";
    }

}
