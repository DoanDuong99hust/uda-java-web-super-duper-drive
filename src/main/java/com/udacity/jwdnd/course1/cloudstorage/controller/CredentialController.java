package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController extends HomeController {

    @PostMapping("/create")
    public String addNew() {

        return "newCredential";
    }

    @GetMapping("/view")
    public String view() {
        return "";
    }

    @PutMapping("/edit/{id}")
    public String edit(@PathVariable Integer id) {
        return "editCredential";
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {

    }
}
