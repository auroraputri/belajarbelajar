package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.RoleModel;
import apap.tutorial.belajarbelajar.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    public String encrypt(String password);
    List<UserModel> findAll();
    UserModel findByUsername(String username);
    String deleteUser(UserModel user);
    boolean passwordEqual(String userPassword, String formPassword);
    void setPassword(UserModel user, String newPassword);
}
