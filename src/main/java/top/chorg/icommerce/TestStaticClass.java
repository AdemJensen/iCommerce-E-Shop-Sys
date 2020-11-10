package top.chorg.icommerce;

import java.util.Map;

public class TestStaticClass {

    public static boolean successAuthTest(Map<String, Object> session) {
        return true;
    }

    public static boolean failureAuthTest(Map<String, Object> session) {
        return false;
    }

    public static String getOkStr(Map<String, Object> session) {
        return "OK";
    }

}
