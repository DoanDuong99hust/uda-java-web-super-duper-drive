package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends ModelAndView {

    private final FileMapper fileMapper;
    private final NoteMapper noteMapper;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final UserMapper userMapper;

    public HomeController(FileMapper fileMapper, NoteMapper noteMapper, CredentialMapper credentialMapper, EncryptionService encryptionService, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.noteMapper = noteMapper;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        Users user = userMapper.findByUsername(authentication.getName());
        model.addAttribute("notes", noteMapper.findAllByUserId(user.getUserId()));
        model.addAttribute("files", fileMapper.findAllByUserId(user.getUserId()));
        model.addAttribute("credentials", credentialMapper.findAllByUserId(user.getUserId()));
        model.addAttribute("EncryptionService", encryptionService);
        return "home";
    }
}
