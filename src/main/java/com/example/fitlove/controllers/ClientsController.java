package com.example.fitlove.controllers;

import com.example.fitlove.models.Clients;
import com.example.fitlove.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    // Получение всех клиентов
    @GetMapping
    public String listClients(Model model) {
        List<Clients> clients = clientsService.getAllClients();
        model.addAttribute("clients", clients);
        return "client_list"; // Используем правильное название
    }

    // Получение формы для добавления нового клиента
    @GetMapping("/new")
    public String createClientForm(Model model) {
        model.addAttribute("client", new Clients());
        return "client_create"; // Используем правильное название
    }

    // Обработка сохранения нового клиента
    @PostMapping
    public String saveClient(@ModelAttribute Clients client) {
        clientsService.saveClient(client);
        return "redirect:/clients"; // Перенаправление на список клиентов
    }

    // Получение формы для редактирования клиента
    @GetMapping("/{id}/edit")
    public String editClientForm(@PathVariable int id, Model model) {
        Optional<Clients> clientOptional = clientsService.getClientById(id);
        if (clientOptional.isPresent()) {
            model.addAttribute("client", clientOptional.get());
            return "client_edit"; // Используем правильное название
        } else {
            return "redirect:/clients"; // Если клиент не найден, перенаправление на список клиентов
        }
    }

    // Обработка обновления клиента
    @PostMapping("/{id}")
    public String updateClient(@PathVariable int id, @ModelAttribute Clients client) {
        client.setId(id); // Устанавливаем ID клиента перед обновлением
        clientsService.updateClient(client);
        return "redirect:/clients"; // Перенаправление на список клиентов
    }

    // Обработка удаления клиента
    @PostMapping("/{id}/delete")
    public String deleteClient(@PathVariable int id) {
        clientsService.deleteClient(id);
        return "redirect:/clients"; // Перенаправление на список клиентов
    }
}