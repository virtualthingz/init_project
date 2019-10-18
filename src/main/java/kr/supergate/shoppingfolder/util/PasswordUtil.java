package kr.supergate.shoppingfolder.util;


import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.regex.Pattern;

public class PasswordUtil {

    private static final Pattern HAS_UPPER_CASE = Pattern.compile("[A-Z]");
    private static final Pattern HAS_LOWER_CASE = Pattern.compile("[a-z]");
    private static final Pattern HAS_NUMBER = Pattern.compile("\\d");

    public static String generatePassword() {
        // approximately 6 ~ 9 of alphanumeric letters
        return generatePassword(6, 2);
    }

    public static String generatePassword(int minLength, int upperCaseCount) {
        String password = getAlphanumericWithUpperCaseLetter(minLength, upperCaseCount);

        boolean hasUpperCase = HAS_UPPER_CASE.matcher(password).find();
        boolean hasLowerCase = HAS_LOWER_CASE.matcher(password).find();
        boolean hasNumber = HAS_NUMBER.matcher(password).find();

        SecureRandom random = new SecureRandom();
        if(!hasUpperCase)
            password += (char)('A' + random.nextInt(26));
        if(!hasLowerCase)
            password += (char)('a' + random.nextInt(26));
        if(!hasNumber)
            password += (char)('0' + random.nextInt(9));

        return password;
    }

    private static String getAlphanumericWithUpperCaseLetter(int length, int upperCaseCount) {
        int bits = length * 5; // base36
        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder(new BigInteger(bits, random).toString(36)); // base36

        for (int i = 0; i < upperCaseCount; i++) {
            int idx = random.nextInt(passwordBuilder.length());
            char c = passwordBuilder.charAt(idx);
            if (Character.isLetter(c))
                passwordBuilder.setCharAt(idx, Character.toUpperCase(c));
            else
                passwordBuilder.setCharAt(idx, (char)('A' + random.nextInt(26)));
        }

        return passwordBuilder.toString();
    }

    public static boolean isValidPassword(String password) {
        return !StringUtils.isEmpty(password)
                && password.length() >= 6
                && password.length() <= 20
                && (
                    isUpperCasedAlphanumeric(password)
                    || isLowerCasedAlphanumeric(password)
                );
    }

    private static boolean isLowerCasedAlphanumeric(String password) {
        return HAS_LOWER_CASE.matcher(password).find()
            && HAS_NUMBER.matcher(password).find();
    }

    private static boolean isUpperCasedAlphanumeric(String password) {
        return HAS_UPPER_CASE.matcher(password).find()
            && HAS_NUMBER.matcher(password).find();
    }
}
