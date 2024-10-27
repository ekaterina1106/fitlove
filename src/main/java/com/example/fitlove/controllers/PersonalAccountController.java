package com.example.fitlove.controllers;

import com.example.fitlove.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/personalAccount")
public class PersonalAccountController {
    private final ClientsService clientsService;

    @Autowired
    public PersonalAccountController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping
    public String ShowPersonalAccount(Model model, Principal principal) {
        model.addAttribute("client", clientsService.getUserByPrincipal(principal));
        return "/personalAccount";
    }
}
