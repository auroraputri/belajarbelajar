package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.RoleModel;
import apap.tutorial.belajarbelajar.model.UserModel;
import apap.tutorial.belajarbelajar.service.RoleService;
import apap.tutorial.belajarbelajar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/add")
    private String addUserFormPage(Model model){
        UserModel user = new UserModel();
        List<RoleModel> listRole = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);;
        return "form-add-user";
    }

    @PostMapping(value = "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model){
        user.setIsSso(false);
        userService.addUser(user);
        model.addAttribute("user", user);;
        return "redirect:/";
    }

    @GetMapping(value = "/viewall")
    private String viewAllUser(Model model){
        List<UserModel> listUser = userService.findAll();
        model.addAttribute("listUser", listUser);;
        return "viewAll-user";
    }

    @GetMapping(value = "/delete/{username}")
    public String deleteUser(@PathVariable String username, @ModelAttribute UserModel Objuser, Model model){
        UserModel user = userService.findByUsername(username);
        String deleteU = userService.deleteUser(user);

        if(deleteU.equals("done")){
            model.addAttribute("status","Berhasil men-delete User");
        }
        else{
            model.addAttribute("status", "Gagal untuk men-delete User");
        }
        return "delete-user";
    }

    @GetMapping("/updatePassword/{username}")
    private String updatePasswordForm(@PathVariable String username) {
        UserModel user = userService.findByUsername(username);
        return "form-update-password";
    }

    @PostMapping("/updatePassword")
    private String updatePasswordFormSubmit( @ModelAttribute UserModel user, String newPass, String confirmPass, Model model) {
        UserModel myUser = userService.findByUsername(user.getUsername());
        String status = "";
        if (userService.passwordEqual(user.getPassword(), myUser.getPassword())){
            if (newPass.equals(confirmPass)){
                userService.setPassword(myUser, newPass);
                userService.addUser(myUser);
                status += "Password berhasil terupdate";
            }
            else {
                status += "Password baru dan Konfirmasi Password tidak sama";
            }
        }
        else {
            status += "Password yang anda masukan salah";
        }
        model.addAttribute("status", status);
        model.addAttribute("username", user.getUsername());
        return "update-password";
    }

}
