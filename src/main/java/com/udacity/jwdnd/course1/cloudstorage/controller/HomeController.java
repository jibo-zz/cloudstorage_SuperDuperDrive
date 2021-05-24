package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private FileService fileService;

    @GetMapping(value = {"/", "/home"})
    public ModelAndView getHomePage(Authentication authentication) throws  Exception {
        User user = (User) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("notes", noteService.getAllNotes(user.getUserid()));
        modelAndView.addObject("credentials", credentialService.getAllCredentials(user.getUserid()));
        modelAndView.addObject("files", fileService.getAllFiles(user.getUserid()));
        return modelAndView;
    }
}