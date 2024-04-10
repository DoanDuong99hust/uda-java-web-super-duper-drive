package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/result")
public class ResultController {

    @GetMapping
    public ModelAndView showResult(Model model, @ModelAttribute("result") Result result) {
        model.addAttribute("isSuccessful", result.isSuccessful());
        model.addAttribute("message", result.getMessage());
        model.addAttribute("redirectTo", result.getRedirectTo());

        return new ModelAndView("result");
    }
}
