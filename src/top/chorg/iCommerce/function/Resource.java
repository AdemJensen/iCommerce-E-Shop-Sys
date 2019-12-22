package top.chorg.iCommerce.function;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.api.Item;
import top.chorg.iCommerce.api.ItemType;
import top.chorg.iCommerce.database.ItemData;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Connection;

public class Resource {

    public static String getResource(HttpServletRequest request, String path) {
        return request.getContextPath() + "/" + path;
    }

    public static String getImg(HttpServletRequest request, String path) {
        return getResource(request, "assets/img/" + path);
    }

    public static String getPBPic(HttpServletRequest request, int id) {
        return getImg(request, "picture-bed/" + id + ".png");
    }

    public static String getItemCover(HttpServletRequest request, int imgId) {
        if (!new File(Config.getPBPic(request, imgId)).exists()) return getImg(request, "item-cover-default.png");
        return getPBPic(request, imgId);
    }

    public static String getTypeCover(HttpServletRequest request, int imgId) {
        if (!new File(Config.getPBPic(request, imgId)).exists()) return getImg(request, "type-cover-default.png");
        return getPBPic(request, imgId);
    }

}
