package service;

import models.EGender;
import models.User;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public interface IUserService {
    List<User> getAll();
    User findUserById(int id);
    User findUserByUsername(String strUsername);
    List<User> findUserByName(String strName);
    List<User> findUserByPhone(String strPhone);
    List<User> findUserByDOB(LocalDate localDate);
    List<User> findUserByGender(EGender gender);
    void updateUser(int id, User user);
    void deleteUser(int id);
    void createUser(User user);
    boolean checkUsernamePassword(String strUsername, String strPassword);

}
