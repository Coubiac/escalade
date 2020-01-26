package com.begr.escalade.controller;


import com.begr.escalade.entity.User;
import com.begr.escalade.service.LuceneIndexService;
import com.begr.escalade.service.UserService;
import com.begr.escalade.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final Logger logger = (Logger) LoggerFactory.getLogger(LuceneIndexService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "security/registration";
    }

    @PostMapping(value = "/register")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "security/registration";
        }

        userService.save(userForm);

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            logger.debug("=====   Erreur de login   =====");
            model.addAttribute("error", "Votre nom d'utilisateur ou mot de passe est invalide.");
        }

        if (logout != null) {
            model.addAttribute("message", "Vous avez été déconnecté.");
        }
        return "security/login";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage(){
        logger.debug("=====   Accès refusé   =====");
        return "error_pages/403";
    }
}