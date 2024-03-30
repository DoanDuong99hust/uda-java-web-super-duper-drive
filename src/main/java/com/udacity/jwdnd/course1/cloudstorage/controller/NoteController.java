package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController extends HomeController {

    @PostMapping("/create")
    public String create() {
        return "createNote";
    }

    @GetMapping("/view")
    public String view() {
        return "viewNote";
    }

    @PutMapping("/edit/{id}")
    public String edit(@PathVariable Integer id) {
        return "editNote";
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {

    }
}
