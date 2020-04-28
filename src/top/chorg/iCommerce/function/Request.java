package top.chorg.iCommerce.function;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.function.auth.AuthInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Request {
    public static String post(String url, String parameters) throws IOException {
        URL postUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);    //post这个地方设置为true
        connection.setRequestMethod("POST");
        connection.setUseCaches(false); // Post 请求不能使用缓存
        connection.setInstanceFollowRedirects(true);// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐式的进行connect。
        connection.connect();
        //--------------------------传参-------------------------
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(parameters);
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        connection.disconnect();
        return result.toString();
    }

    /**
     * 在不改变地址栏的情况下将页面跳转到info界面。
     *
     * @param request request对象，用于获取info页面地址
     * @param simple 是否使用简单信息页面，复杂信息页面需要 config 文件到位
     * @param type  信息类型，可以是success, warning, error三种之一。
     * @param title 显示在标题栏上的文字内容
     * @param info  显示在主页面的h1内容，颜色由type决定
     * @param hint  显示在h1下面的内容，一般显示具体错误信息
     * @param callBack  自动跳转页面目标。
     *                  如果type不为success，则不会显示callBack，也不会自动跳转。
     *                  如果不想设置跳转，请传入空字符串。
     * @throws IOException 获取信息时出现错误。
     */
    public static void becomeInfoPage(HttpServletRequest request, HttpServletResponse response, boolean simple,
                                      String type, String title, String info, String hint, String callBack
    ) throws IOException, ServletException {
        //if (!Config.configFileExists(request)) simple = true;
        String content = "info_type=" + URLEncoder.encode(type, String.valueOf(StandardCharsets.UTF_8));
        content += "&title="  + URLEncoder.encode(title, String.valueOf(StandardCharsets.UTF_8));
        content += "&info_str="  + URLEncoder.encode(info, String.valueOf(StandardCharsets.UTF_8));
        content += "&info_hint="  + URLEncoder.encode(hint, String.valueOf(StandardCharsets.UTF_8));
        if (callBack != null && callBack.length() > 0)
            content += "&call_back="  + URLEncoder.encode(callBack, String.valueOf(StandardCharsets.UTF_8));
        if (simple) {
            request.getRequestDispatcher("/info-min?" + content).forward(request, response);
        } else {
            request.getRequestDispatcher("/info?" + content).forward(request, response);
        }
    }
    public static void becomeInfoPage(HttpServletRequest request, HttpServletResponse response,
                                      String type, String title, String info, String hint
    ) throws IOException, ServletException {
        becomeInfoPage(request, response, true, type, title, info, hint, "-1");
    }

    public static void setLastVis(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder("?");
        Map<String, String[]> map = request.getParameterMap();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String[] values = map.get(key);
            for (String value : values) {
                builder.append(key);
                builder.append("=");
                builder.append(value);
                builder.append("&");
            }
        }
        builder.replace(builder.length() - 1, builder.length(), "");
        request.getSession(true).setAttribute("LAST_VIS", request.getRequestURI() + builder.toString());
    }
    public static String getLastVis(HttpServletRequest request) {
        Object obj = request.getSession(true).getAttribute("LAST_VIS");
        if (obj == null) return request.getContextPath();
        else return (String) obj;
    }

    public static boolean validateAdminLevel(HttpServletRequest request, HttpServletResponse response, int level) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (!AuthInfo.isAdmin(session)) {
            Request.becomeInfoPage(
                    request, response,
                    "error", "错误", "无访问权限", "您还未<a href='" + request.getContextPath() + "/admin/login'>登录管理员账户</a>，无权限操作"
            );
            return false;
        } else {
            int aLevel = AuthInfo.getAdminLevel(session);
            if (aLevel < level) {
                Request.becomeInfoPage(
                        request, response,
                        "error", "错误", "无访问权限", "访问此页面需要权限级别【" + level + "】以上，您的权限级别为【" + aLevel + "】"
                );
                return false;
            }
        }
        return true;
    }

}
