package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.RoleModel;
import apap.tutorial.belajarbelajar.model.UserModel;
import apap.tutorial.belajarbelajar.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<UserModel> findAll() {
        List<UserModel> listUser = userDb.findAll();
        return listUser;
    }

    @Override
    public UserModel findByUsername(String username) {
        UserModel user = userDb.findByUsername(username);
        return user;
    }

    @Override
    public String deleteUser(UserModel user) {
        userDb.delete(user);
        return "done";
    }

    @Override
    public boolean passwordEqual(String userPassword, String formPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(userPassword,formPassword);
    }

    @Override
    public void setPassword(UserModel user, String newPassword){
        user.setPassword(newPassword);
    }

}
