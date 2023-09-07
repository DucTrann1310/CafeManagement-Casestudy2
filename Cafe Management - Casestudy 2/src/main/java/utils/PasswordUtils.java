package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static boolean isValid(String passwordCheck, String password){
        // password: $2a$12$OFOICietLS3.qRtzIe6jE.vF.fmtL22DqIZ18WNMmQ.8nS7Frq5aO <= "123444"
        // passwordCheck: "123444"
        return BCrypt.checkpw(passwordCheck, password);
    }

    public static String generatePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

//    public static void main(String[] args) {
//        System.out.println(generatePassword("123123"));
//    }
}
