package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/file")
public class FileController extends HomeController{

    @PostMapping("/upload")
    public String upload() {
        // TODO: validate file existed or not by filename -> show error
        return "upload";
    }

    @GetMapping("/view-detail")
    public String view(Model model) {

        return "filedetail";
    }

    @GetMapping("/download")
    public void download(Model model) {

    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {

    }

}
