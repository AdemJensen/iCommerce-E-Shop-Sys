package top.chorg.icommerce.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class NavigationUtils {
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
    public static String getLastVis(HttpSession session) {
        Object obj = session.getAttribute("LAST_VIS");
        if (obj == null) return "/";
        else return (String) obj;
    }

}
