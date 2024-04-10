package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.Result;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/create-update")
    public ModelAndView createAndUpdate(@ModelAttribute("credential")Credentials credential,
                                        RedirectAttributes redirectAttributes,
                                        Authentication authentication) {
        Result result;
        try {
            Users user = userMapper.findByUsername(authentication.getName());
            Credentials existedCredential = credentialMapper.findById(credential.getCredentialId());
            if (existedCredential != null) {
                existedCredential.setPassword(encryptionService.encryptValue(credential.getPassword(),
                        encryptionService.convertKeyStringToByteArray(existedCredential.getKey())));
                existedCredential.setUrl(credential.getUrl());
                existedCredential.setUsername(credential.getUsername());
                credentialMapper.update(existedCredential);
            } else {
                byte[] keyByte = encryptionService.generateRandomKey("AES", 256).getEncoded();
                credential.setPassword(encryptionService.encryptValue(credential.getPassword(), keyByte));
                credential.setKey(encryptionService.convertKeyByteArrayToString(keyByte));
                credential.setUserId(user.getUserId());
                credentialMapper.create(credential);
            }
            result = new Result(true, CommonConstant.SUCCESSFUL_SAVED_MESSAGE, "/home");
        } catch (Exception e) {
            result = new Result(false, CommonConstant.NOT_SUCCESSFUL_SAVED_MESSAGE, "/home");
        }
        redirectAttributes.addFlashAttribute("result", result);
        return new ModelAndView("redirect:/result");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id) {
        credentialMapper.delete(id);
        return new ModelAndView("redirect:/home");
    }
}
