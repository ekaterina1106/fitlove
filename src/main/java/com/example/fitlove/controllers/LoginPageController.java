package com.example.fitlove.controllers;

import com.example.fitlove.models.Clients;
import com.example.fitlove.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import java.security.Principal;

@Controller
public class LoginPageController {

    private final ClientsService clientsService;

    @Autowired
    public LoginPageController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/login")
    public String loginPage(Model model, Principal principal,
                            @RequestParam(name = "error", required = false) String error,
                            @RequestParam(name = "logout", required = false) String logout) {
        Clients client = new Clients(); // создаем новый объект клиента для формы
        model.addAttribute("client", clientsService.getUserByPrincipal(principal));

        if (error != null) {
            model.addAttribute("errorMessage", "Неверный логин или пароль");
        } else if (logout != null) {
            model.addAttribute("errorMessage", "Вы успешно вышли");
        }

        return "login"; // возвращаем название html страницы входа
    }

    @PostMapping("/login")
    public String login(@Valid Clients client, BindingResult bindingResult, Model model, Principal principal) {
        model.addAttribute("client", clientsService.getUserByPrincipal(principal));
        if (bindingResult.hasErrors()) {
            // Обработка ошибок валидации
            model.addAttribute("emailError", bindingResult.getFieldError("email").getDefaultMessage());
            model.addAttribute("passwordError", bindingResult.getFieldError("password").getDefaultMessage());
            return "login"; // возвращаем на страницу входа с ошибками
        }

        // Здесь можно добавить логику аутентификации, если это необходимо

        return "redirect:/main"; // перенаправление на домашнюю страницу после успешного входа
    }
}

