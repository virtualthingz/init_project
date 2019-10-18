package kr.supergate.shoppingfolder.util;




import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class LoginIdUtil {

    private static final Pattern CN_OUT_OF_LOWER_ALPHANUMERIC = Pattern.compile("[^a-z0-9\\u4e00-\\u9fa5]");

    private static final Pattern OUT_OF_LOWER_ALPHANUMERIC = Pattern.compile("[^a-z0-9]");


    public static boolean isValidLoginId(String loginId) {
        return !StringUtils.isEmpty(loginId)
                && loginId.length() >= 5
                && loginId.length() <= 12
                && !OUT_OF_LOWER_ALPHANUMERIC.matcher(loginId).find();
    }

    public static boolean isValidLoginCnId(String loginId) {
        return !StringUtils.isEmpty(loginId)
                && loginId.length() >= 5
                && loginId.length() <= 12
                && !CN_OUT_OF_LOWER_ALPHANUMERIC.matcher(loginId).find();
    }
}
