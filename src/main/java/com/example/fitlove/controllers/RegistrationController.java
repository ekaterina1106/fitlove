package com.example.fitlove.controllers;

import com.example.fitlove.models.Clients;
import com.example.fitlove.repositories.ClientsRepository;
import com.example.fitlove.services.ClientsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {
    private final ClientsService clientsService;

   private final ClientsRepository clientsRepository;

    public RegistrationController(ClientsService clientsService, ClientsRepository clientsRepository) {
        this.clientsService = clientsService;
        this.clientsRepository = clientsRepository;
    }

    @GetMapping("/registration")
    public String registration(Model model, Principal principal){
        model.addAttribute("title", "Страница регистрации");
        model.addAttribute("client", clientsService.getUserByPrincipal(principal));
        System.out.println("get method");
        return "registration";

    }


    @PostMapping("/registration")
    public String createUser(@Valid Clients client, BindingResult bindingResult, Model model, HttpSession session) {
        model.addAttribute("client", client);

        // Проверка длины пароля
        if (client.getPassword().length() < 4) {
            bindingResult.rejectValue("password", "error.client", "Пароль должен быть не менее 4 символов.");
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            fieldError -> fieldError.getField() + "Error",
                            FieldError::getDefaultMessage
                    ));
            model.mergeAttributes(errorsMap);
            return "registration";
        } else {
            String message = clientsService.createUser(client);
            if (message.equals("true")) {
                session.setAttribute("successMessage", "Успешная регистрация");
                return "redirect:/login";
            } else {
                model.addAttribute("errorMessage", "Аккаунт с такой почтой уже существует.");
                return "registration";
            }
        }
    }



//    @PostMapping("/registration")
//    public String createUser(@Valid Clients client, BindingResult bindingResult, Model model, HttpSession session){
//        System.out.println("post method");
////        model.addAttribute("user", clientsService.getUserByPrincipal(principal));
//
//        model.addAttribute("client", client);
//        if (bindingResult.hasErrors()){
//            System.out.println("some error in br");
//            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
//                    fieldError -> fieldError.getField() + "Error",
//                    FieldError::getDefaultMessage
//            );
//            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
//            model.mergeAttributes(errorsMap);
//            return "registration";
////            bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
////                    fieldError -> fieldError.getField() + "Error",
////                    FieldError::getDefaultMessage
////            ))
////            model.addAttribute("bindingResult", bindingResult);
////            return "registration";
//        } else {
//            System.out.println("visov createuser");
//            String message = clientsService.createUser(client);
//            if (message.equals("true")){
//                session.setAttribute("successMessage", "Успешная регистрация");
//                System.out.println("success registration");
//                return "redirect:/login";
//            }else {
//                model.addAttribute("errorMessage", "Аккаунт с такой почтой уже есть");
//                model.addAttribute("bindingResult", bindingResult);
//                return "registration";
//            }
//        }
//    }
}
