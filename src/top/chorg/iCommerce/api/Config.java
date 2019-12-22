package top.chorg.iCommerce.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config {
    public String SYSTEM_NAME;
    public boolean USE_MODIFIED_INDEX;
    public String MODIFIED_INDEX_FILE;

    public String CHAT_SYSTEM;

    public String DATABASE_ADDR;
    public int DATABASE_PORT;
    public String DATABASE_USERNAME;
    public String DATABASE_PASSWORD;
    public String DATABASE_SCHEMA;
    public boolean DATABASE_USE_UNICODE;
    public String DATABASE_ENCODING;
    public String DATABASE_TIMEZONE;
    public boolean DATABASE_USE_SSL;
    public boolean PUBLIC_KEY_RETRIEVAL;

    public String DATABASE_TABLE_ADMIN;
    public String DATABASE_TABLE_CART;
    public String DATABASE_TABLE_ITEM;
    public String DATABASE_TABLE_ITEM_TYPE;
    public String DATABASE_TABLE_ITEM_TYPE_BELONG;
    public String DATABASE_TABLE_ORDER;
    public String DATABASE_TABLE_ORDER_BELONG;
    public String DATABASE_TABLE_SHIPPING;
    public String DATABASE_TABLE_USER;

    public static String getCustomFileRelativePath(String fileName) {
        return "/custom/" + fileName;
    }

    public static String getCustomFileAbsolutePath(HttpServletRequest request, String fileName) {
        return request.getServletContext().getRealPath("custom/" + fileName);
    }

    public static String getItemPageFileAbsolutePath(HttpServletRequest request, String fileName) {
        return getCustomFileAbsolutePath(request, "items/" + fileName);
    }

    public static String getItemPageFileRelativePath(String fileName) {
        return getCustomFileRelativePath("items/" + fileName);
    }

    public static String getAssetFileAbsolutePath(HttpServletRequest request, String fileName) {
        return request.getServletContext().getRealPath("assets/" + fileName);
    }

    public static String getImageFileAbsolutePath(HttpServletRequest request, String fileName) {
        return getAssetFileAbsolutePath(request, "img/" + fileName);
    }

    public static String getPBPic(HttpServletRequest request, int id) {
        return getImageFileAbsolutePath(request, "picture-bed/" + id + ".png");
    }

    public static Config readConfFromFile(HttpServletRequest request) throws FileNotFoundException {
        return readConfFromFile(request, "config.json");
    }

    public static Config readConfFromFile(HttpServletRequest request, String path) throws FileNotFoundException {
        Gson gson = new Gson();
        Config conf = new Config();
        String contextPath = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
        return gson.fromJson(new FileReader(request.getServletContext().getRealPath(path)), Config.class);
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void saveToFile(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(file);
            o.write(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
