package service;

import models.EGender;
import models.User;
import utils.FileUtils;
import utils.PasswordUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService{
    private final String fileUser = "./Data/users.txt";
    @Override
    public List<User> getAll() {
        return FileUtils.readData(fileUser,User.class);
    }

    @Override
    public User findUserById(int id) {
        List<User> users = getAll();
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public User findUserByUsername(String strUsername) {
        List<User> users = getAll();
        return users.stream().filter(u -> u.getUsername().equals(strUsername)).findFirst().orElse(null);
    }

    @Override
    public List<User> findUserByName(String strName) {
        List<User> users = getAll();
        return users.stream().filter(u -> u.getName().equals(strName)).collect(Collectors.toList());
    }

    @Override
    public List<User> findUserByPhone(String strPhone) {
        List<User> users = getAll();
        return users.stream().filter(u -> u.getPhone().equals(strPhone)).collect(Collectors.toList());
    }

    @Override
    public List<User> findUserByDOB(LocalDate localDate) {
        List<User> users = getAll();
        return users.stream().filter(u -> u.getDob().equals(localDate)).collect(Collectors.toList());
    }

    @Override
    public List<User> findUserByGender(EGender gender) {
        List<User> users = getAll();
        return users.stream().filter(u -> u.getGender() == gender).collect(Collectors.toList());
    }

    @Override
    public void updateUser(int id, User user) {
        List<User> users = getAll();
        for (User u : users) {
            if (u.getId() == id) {
                u.setName(user.getName());
                u.setDob(user.getDob());
                u.setGender(user.getGender());
                u.setPhone(user.getPhone());
                break;
            }
        }
        FileUtils.writeData(fileUser,users);
    }

    @Override
    public void deleteUser(int id) {
        List<User> users = getAll();
        users.removeIf(u -> u.getId() == id);
        users.subList(id-1, users.size()).stream().forEach(user -> user.setId(user.getId()-1));
        FileUtils.writeData(fileUser,users);
    }

    @Override
    public void createUser(User user) {
        List<User> users = getAll();
        users.add(user);

        FileUtils.writeData(fileUser,users);
    }

    @Override
    public boolean checkUsernamePassword(String strUsername, String strPassword) {
        List<User> users = getAll();
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(strUsername)
                        && PasswordUtils.isValid(strPassword, user.getPassword()));
    }

}
