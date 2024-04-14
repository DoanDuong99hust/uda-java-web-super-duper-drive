package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Result;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileController(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
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
    public ModelAndView upload(@RequestParam("fileUpload") MultipartFile inputFile,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes,
                               Model model) throws IOException {
        Result result;
        String view;
        if (!inputFile.isEmpty()) {
            InputStream fileInputStream = inputFile.getInputStream();
            try {
                Users user = userMapper.findByUsername(authentication.getName());
                Files file = fileMapper.findByName(inputFile.getOriginalFilename());
                if (file == null) {
                    fileMapper.insert(new Files(inputFile.getOriginalFilename(), inputFile.getContentType(), inputFile.getSize(),
                            fileInputStream.readAllBytes(), user.getUserId()));
                    result = new Result(true, CommonConstant.SUCCESSFUL_SAVED_MESSAGE, "/home");
                } else {
                    result = new Result(false, "Filename already existed!", "/home");
                }
            } catch (Exception e) {
                result = new Result(false, e.getMessage(), "/home");
            }
            view = "redirect:/result";
            model.addAttribute("isEmptyFile", false);
            redirectAttributes.addFlashAttribute("result", result);
        } else {
            view = "/home";
            model.addAttribute("isEmptyFile", true);
        }

        return new ModelAndView(view);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Integer id) {
            Files file = fileMapper.findById(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(new ByteArrayResource(file.getFileData()));
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id) {
        fileMapper.delete(id);
        return new ModelAndView("redirect:/home");
    }

}
