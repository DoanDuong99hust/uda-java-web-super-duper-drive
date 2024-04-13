package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.entity.Notes;
import com.udacity.jwdnd.course1.cloudstorage.entity.Result;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    public NoteController(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/create-update")
    public ModelAndView create(@ModelAttribute("note") Notes notes,
                               RedirectAttributes redirectAttributes,
                               Authentication authentication) {
        Result result;
        try {
            Users user = userMapper.findByUsername(authentication.getName());
            Notes existingNote = noteMapper.findById(notes.getNoteId());
            if (existingNote != null) {
                existingNote.setNoteTitle(notes.getNoteTitle());
                existingNote.setNoteDescription(notes.getNoteDescription());
                noteMapper.update(existingNote);
            } else {
                noteMapper.insert(new Notes(notes.getNoteTitle(), notes.getNoteDescription(), user.getUserId()));
            }
            result = new Result(true, CommonConstant.SUCCESSFUL_SAVED_MESSAGE, "/home");
        } catch (Exception e) {
            result = new Result(false, e.getMessage(), "/home");
        }
        redirectAttributes.addFlashAttribute("result", result);
        return new ModelAndView("redirect:/result");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        noteMapper.delete(id);
        redirectAttributes.addFlashAttribute("result", new Result(true, CommonConstant.SUCCESSFUL_SAVED_MESSAGE, "/home"));
        return new ModelAndView("redirect:/result");
    }
}