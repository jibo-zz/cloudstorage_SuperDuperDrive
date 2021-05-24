package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PostMapping("/credentials")
    public String saveOrUpdateCredentials(Authentication authentication, Credential credential) {
        User user = (User) authentication.getPrincipal();
        if (credential.getCredentialid() > 0) {
            credentialService.updateCredential(credential);
        }
        else {
            credentialService.addCredential(credential, user.getUserid());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/credentials/delete")
    public String deleteNote(@RequestParam("id") int credentialid) {
        if (credentialid > 0) {
            credentialService.deleteCredential(credentialid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}