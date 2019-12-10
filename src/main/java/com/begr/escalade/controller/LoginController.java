package com.begr.escalade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

    @RequestMapping("/showMyLoginPage")
    public String showMyLoginPage(){
        return "security/plain-login";
    }


    @GetMapping("/access-denied")
    public String showAccessDenied(){
        return "error_pages/403";
    }

}
