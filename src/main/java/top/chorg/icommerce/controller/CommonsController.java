package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import top.chorg.icommerce.common.GeneralContext;

@Controller
public class CommonsController {

    final GeneralContext generalContext;

    public CommonsController(GeneralContext generalContext) {
        this.generalContext = generalContext;
    }

    /**
     * Response with a info page.
     * Automatically linked with '/info'.
     *
     * @param model Template engine, can pass from top.
     * @param title Text to display on the title bar.
     * @param resType The type of this info page.
     *                "success" = Green page representing success.
     *                "warning" = Yellow page representing warning.
     *                "danger" = Red page representing error.
     * @param resStr Text to display on the master info area, below the large icon.
     * @param resInfo Text to display below <b>resStr</b>, with black text and smaller font.
     * @param callBack The address for auto redirect.
     *                 If you don't want callBack, you can pass a <b>null</b>.
     *                 If you want your user to go back to the last visit position, pass <b>"-1"</b>.
     * @param lastVisit Usually passed via session.
     * @return The path of template page.
     */
    @RequestMapping(value = "/info")
    public String info(
            Model model,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "info_type", required = false) String resType,
            @RequestParam(value = "info_str", required = false) String resStr,
            @RequestParam(value = "info_hint", required = false) String resInfo,
            @RequestParam(value = "call_back", required = false) String callBack,
            @SessionAttribute(value = "LAST_VIS", required = false) String lastVisit
    ) {
        model.addAttribute("general", generalContext);
        model.addAttribute("title", title == null ? "错误" : title);
        String resIconHtml = "<svg width=\"10em\" height=\"10em\" viewBox=\"0 0 16 16\" class=\"bi bi-x-circle\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "  <path fill-rule=\"evenodd\" d=\"M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z\"/>\n" +
                "  <path fill-rule=\"evenodd\" d=\"M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z\"/>\n" +
                "</svg>";
        if (resType == null || resStr == null) {
            resType = "text-danger";
        } else {
            switch (resType) {
                case "success":
                    resIconHtml = "<svg width=\"10em\" height=\"10em\" viewBox=\"0 0 16 16\" class=\"bi bi-check-circle\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                            "  <path fill-rule=\"evenodd\" d=\"M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z\"/>\n" +
                            "  <path fill-rule=\"evenodd\" d=\"M10.97 4.97a.75.75 0 0 1 1.071 1.05l-3.992 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.236.236 0 0 1 .02-.022z\"/>\n" +
                            "</svg>";
                    resType = "text-success";
                    break;
                case "warning":
                    resIconHtml = "<svg width=\"10em\" height=\"10em\" viewBox=\"0 0 16 16\" class=\"bi bi-exclamation-circle\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                            "  <path fill-rule=\"evenodd\" d=\"M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z\"/>\n" +
                            "  <path d=\"M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z\"/>\n" +
                            "</svg>";
                    resType = "text-warning";
                    break;
                default:
                    resType = "text-danger";
            }
        }
        model.addAttribute("resIconHtml", resIconHtml);
        model.addAttribute("resType", resType);
        if (resStr == null) resStr = "错误";
        model.addAttribute("resStr", resStr);
        if (resInfo == null) resInfo = "未授权的访问";
        model.addAttribute("resInfo", resInfo);
        if (callBack == null) callBack = "";
        if (callBack.equals("-1")) {
            if (lastVisit == null) callBack = "/";
            else callBack = lastVisit;
        }
        model.addAttribute("callBack", callBack);
        model.addAttribute("shouldCountDown", callBack.length() > 0);
        return "commons/info";
    }


}
