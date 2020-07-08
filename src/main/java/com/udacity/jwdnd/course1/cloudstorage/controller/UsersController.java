package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;


class LoginForm{

}

class SignupForm{
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
@Controller
public class UsersController {

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model){return "login";}

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("loginForm") LoginForm loginForm, Model model){

        return "login";}


    @GetMapping("/signup")
    public String signin(@ModelAttribute("signupForm") SignupForm signupForm, Model model ){return "signup";}

    @PostMapping("/signup")
    public String postSignin(@ModelAttribute("signupForm") SignupForm signupForm, Model model ){

        String signupError = null;

        if (!userService.isUsernameAvailable(signupForm.getUsername())) {
            signupError = "The username already exists.";
        }

        User newUser = new User(null,signupForm.getUsername(),null,signupForm.getPassword(), signupForm.getFirstName(), signupForm.getLastName());

        if (signupError == null) {
            int rowsAdded = userService.createUser(newUser);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request){
//        request.getSession().invalidate();
//        return "redirect:/login";
//    }
}
