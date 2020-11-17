package top.chorg.icommerce.common;

import top.chorg.icommerce.common.enums.UserType;

public class URLHelper {

    public static String getUserAvatarUrl(UserType userType, int userId) {
        return String.format(
                "@{/api/avatar/get/%s/%s}",
                StringRules.getUserAvatarUrlPrefixString(userType),
                StringRules.getUserAvatarUploadFilename(userType, userId)
        );
    }

}
