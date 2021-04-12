package by.ny.client.util;

public class Validator {
    private static final String CHECK_EMAIL = "[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@" +
            "([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*([a-z]{2,4})";
    private static final String CHECK_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final String CHECK_PHONE_NUMBER = "^((8|\\+375)[\\- ]?)?(\\(?\\d{2}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public static boolean isCorrectEmail(String email) {
        return email.matches(CHECK_EMAIL);
    }

    public static boolean isCorrectPassword(String password) {
        return password.matches(CHECK_PASSWORD);
    }

    public static boolean isCorrectLogin(String login) {
        return login.length() <= MAX_LOGIN_LENGTH;
    }

    public static boolean isCorrectPhoneNumber(String number) {
        return number.matches(CHECK_PHONE_NUMBER);
    }
}
