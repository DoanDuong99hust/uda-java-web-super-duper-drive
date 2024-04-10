package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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

    public HomeController(FileMapper fileMapper, NoteMapper noteMapper, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.fileMapper = fileMapper;
        this.noteMapper = noteMapper;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("notes", noteMapper.findAll());
        model.addAttribute("files", fileMapper.findAll());
        model.addAttribute("credentials", credentialMapper.findAll());
        model.addAttribute("EncryptionService", encryptionService);
        return "home";
    }
}
