package utils;

import models.ERole;
import models.User;

public class AuthUtils {
    private static User user;
    public static void setUserAuthenticator(User user){
        AuthUtils.user = user;
    }
    public static User getUserAuthenticator(){
        return user;
    }
    public static boolean hasRole(ERole eRole){
        return user.getRole().equals(eRole);
    }
}
