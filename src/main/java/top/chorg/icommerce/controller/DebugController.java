package top.chorg.icommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import top.chorg.icommerce.common.GeneralContext;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/debug")
public class DebugController {

    private static final Logger LOG = LoggerFactory.getLogger(DebugController.class);

    final CommonsController commonsController;
    final GeneralContext generalContext;

    public DebugController(CommonsController commonsController, GeneralContext generalContext) {
        this.commonsController = commonsController;
        this.generalContext = generalContext;
    }

    @RequestMapping(value = "test")
    public String test(Model model) {
        model.addAttribute("general", generalContext);
        return "admin/pageEditor";
    }

    // Session test 1.
    @RequestMapping(value = "session/1")
    public String sessionDebug1(Model model, HttpSession session,
                        @SessionAttribute(value = "LAST_VIS", required = false) String lastVisit) {
        LOG.debug("Requesting debug sequence.");
        session.setAttribute("LAST_VIS", "/test/success");
        return commonsController.info(
                model, "测试", "warning", "测试",
                "正在执行测试", "/debug/session/2", lastVisit
        );
    }

    // Session test 2.
    @RequestMapping(value = "session/2")
    public String sessionDebug2(Model model, HttpSession session,
                        @SessionAttribute(value = "LAST_VIS", required = false) String lastVisit) {
        LOG.debug("Requesting debug sequence.");
        return commonsController.info(
                model, "测试", "success", "测试完成",
                "测试完成", "-1", lastVisit
        );
    }

}
