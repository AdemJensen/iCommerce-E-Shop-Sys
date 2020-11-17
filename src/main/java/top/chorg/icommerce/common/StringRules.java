package top.chorg.icommerce.common;

import top.chorg.icommerce.common.enums.UserType;
import top.chorg.icommerce.common.utils.MD5;
import top.chorg.icommerce.common.utils.RandomStrings;

public class StringRules {

    public static String getUserAvatarUrlPrefixString(UserType userType) {
        switch (userType) {
            case Admin:
                return "a";
            case Customer:
                return "c";
            default:
                return "u";
        }
    }

    public static UserType getUserTypeFromAvatarUrlPrefixString(String prefix) {
        switch (prefix) {
            case "a":
                return UserType.Admin;
            case "c":
                return UserType.Customer;
            default:
                return null;
        }
    }

    public static String getUserAvatarUploadFilename(UserType userType, int userId) {
        return MD5.encode(userType.toString() + userId);
    }

    public static String getAdminUploadFilename(String filename, int uploader) {
        return MD5.encode(filename + uploader + RandomStrings.getRandomString(40));
    }

}
