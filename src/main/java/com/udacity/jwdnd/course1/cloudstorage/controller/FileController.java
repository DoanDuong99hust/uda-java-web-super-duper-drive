package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileMapper fileMapper;

    public FileController(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * The user should be able to upload files and see any files they previously uploaded.
     * The user should be able to view/download or delete previously-uploaded files.
     * Any errors related to file actions should be displayed.
     * For example, a user should not be able to upload two files with the same name, but they'll never know unless you tell them!
     * @param inputFile
     * @return
     */
    @PostMapping("/upload")
    public void upload(@RequestParam("fileUpload") MultipartFile inputFile, Model model) throws IOException {
        InputStream fileInputStream = inputFile.getInputStream();
        //TODO: To get userId after applying security
        fileMapper.insert(new Files(inputFile.getName(), inputFile.getContentType(), inputFile.getSize(),
                fileInputStream.readAllBytes(), 1));
    }

    @GetMapping("/view-detail")
    public String view(Model model) {
        List<Files> files = fileMapper.findAll();
        model.addAttribute("files", files);
        return "home";
    }

    @GetMapping("/download")
    public void download(Model model) {

    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {

    }

}
