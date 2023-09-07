package utils;

import models.User;
import service.IUserService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class UsernameUtils {

    public static boolean isValidUsername(String username, List<User> users){
        for(User u : users){
            if(u.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }
}
