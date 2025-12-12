package gestion_restaurant.util;

public class Validator {
    public static boolean isValidTelephone(String tel) {
        if (tel == null) return false;
        return tel.matches("^(77|78)\\d{7}$");
    }
}
