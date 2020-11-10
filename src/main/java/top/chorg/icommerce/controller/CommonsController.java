package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.chorg.icommerce.common.GeneralContext;
import top.chorg.icommerce.common.utils.Request;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommonsController {

    final GeneralContext generalContext;

    public CommonsController(GeneralContext generalContext) {
        this.generalContext = generalContext;
    }

    @RequestMapping(value = "/info")
    public String error(Model model, HttpServletRequest request) {
        model.addAttribute("general", generalContext);
        String title = request.getParameter("title");
        model.addAttribute("title", title == null ? "错误" : title);
        String resType = request.getParameter("info_type");
        String resStr = request.getParameter("info_str");
        String resInfo = request.getParameter("info_hint");
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
        String callBack = request.getParameter("call_back");
        if (callBack == null) callBack = "";
        if (callBack.equals("-1")) callBack = Request.getLastVis(request);
        model.addAttribute("callBack", callBack);
        model.addAttribute("shouldCountDown", resType.equals("text-success") && callBack.length() > 0);
        return "commons/info";
    }


}
