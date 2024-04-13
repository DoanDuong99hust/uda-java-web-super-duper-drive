package com.udacity.jwdnd.course1.cloudstorage.exception;

import com.udacity.jwdnd.course1.cloudstorage.entity.Result;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = FileSizeLimitExceededException.class)
    protected ModelAndView handleFileUploadLimitedException(Model model, HttpServletRequest req, Exception ex) {
        model.addAttribute("isSuccessful", false);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("redirectTo", "/home");
        return new ModelAndView("result");
    }

    @ExceptionHandler(value = Exception.class)
    protected ModelAndView handleCommonException(Model model, Exception ex) {
        model.addAttribute("isSuccessful", false);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("redirectTo", "/home");
        return new ModelAndView("result");
    }
}
