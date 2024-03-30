package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Controller
public class CloudStorageController {

    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public CloudStorageController(UserMapper userMapper, HashService hashService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("credentials") Credentials credentials) {
        return "login";
    }

    @GetMapping("/sign-up")
    public String getSignUpPage(@ModelAttribute("users") Users users, Model model) {
        model.addAttribute("isSignUpSuccessful", false);
        model.addAttribute("isSignUpFail", false);
        model.addAttribute("errMessage", "");
        return "signup";
    }

    @PostMapping("/sign-up")
    public ModelAndView processSignUp(@ModelAttribute("users") Users users, Model model) {
        String username = users.getUsername();
        String password = users.getPassword();
        String firstName = users.getFirstname();
        String lastName = users.getLastname();

        boolean isSignUpSuccessful = false;
        boolean isSignUpFail = false;
        String errMessage = "";
        Users user = userMapper.findByUsername(username);
        if (Objects.nonNull(user)) {
            isSignUpFail = true;
            errMessage = "User already existed!";
        } else {
            String salt = UUID.randomUUID().toString();
            String hashedPassword = hashService.getHashedValue(password, salt);
            Integer userId = userMapper.insert(new Users(username, salt, hashedPassword, firstName, lastName));

            SecureRandom random = new SecureRandom();
            byte[] key = new byte[32];
            random.nextBytes(key);
            String encryptedValue = encryptionService.encryptValue(password, key);
            credentialMapper.create(new Credentials("login", username, Arrays.toString(key), encryptedValue, userId));
            isSignUpSuccessful = true;
        }
        model.addAttribute("isSignUpSuccessful", isSignUpSuccessful);
        model.addAttribute("isSignUpFail", isSignUpFail);
        model.addAttribute("errMessage", errMessage);

        return new ModelAndView("signup");
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
