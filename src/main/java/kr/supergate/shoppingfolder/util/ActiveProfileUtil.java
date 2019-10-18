package kr.supergate.shoppingfolder.util;

public class ActiveProfileUtil {
    private static String mode = System.getProperty("spring.profiles.active");

    public static boolean isProduction() {
        return "prod".equals(mode);
    }

    public static boolean isAlpha() {
        return "alpha".equals(mode);
    }
    public static boolean isLocationHost() {
        return "localhost".equals(mode);
    }
    public static boolean isDev() {
        return "dev".equals(mode);
    }

    public static boolean isLive() {
        return isAlpha() || isProduction();
    }

    public static boolean isDevelopment() {
        return !isLive();
    }

    public static String sessionProfile() {
        return isProduction() ? "" : System.getProperty("spring.profiles.active") + "_";
    }
}
