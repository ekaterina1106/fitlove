package com.example.fitlove.controllers;

import com.example.fitlove.models.Clients;
import com.example.fitlove.models.GroupClasses;

import com.example.fitlove.services.ClientsService;
import com.example.fitlove.services.EnrollmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.fitlove.services.GroupClassesService;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/schedule")
@Controller
public class ScheduleController {

    private final GroupClassesService groupClassesService;
    private final EnrollmentsService enrollmentsService;
    private final ClientsService clientsService;

    @Autowired
    public ScheduleController(GroupClassesService groupClassesService, EnrollmentsService enrollmentsService, ClientsService clientsService) {
        this.groupClassesService = groupClassesService;
        this.enrollmentsService = enrollmentsService;
        this.clientsService = clientsService;
    }


    @GetMapping()
    public String showSсhedule(Model model, Principal principal) {
        model.addAttribute("client", clientsService.getUserByPrincipal(principal));
        List<LocalDate> weekDates = groupClassesService.getWeekDates();
        model.addAttribute("weekDates", weekDates);
        model.addAttribute("dayOfWeek", groupClassesService.getWeekDays(weekDates));
//        System.out.println(groupClassesService.getWeekDays(weekDates) + " week days");
        model.addAttribute("times", groupClassesService.getTimes());
        model.addAttribute("classes", groupClassesService.getAllGroupClasses());
//        System.out.println(model.getAttribute("classes"));
        return "schedule/schedule";
    }

    @PostMapping("/enroll")
    public String enrollClient(@RequestParam(value = "classId") String classId, Model model, Principal principal) {
        // Получаем клиента по Principal
        Clients client = clientsService.getUserByPrincipal(principal);

        if (client == null) {
            // Если клиент не найден, перенаправляем на страницу логина
            return "redirect:/login";
        }

        if (classId == null || classId.isEmpty()) {
            // Обработка ошибки, если ID занятия не был предоставлен
            return "redirect:/schedule?error=classIdNotProvided";
        }

        try {
            // Записываем клиента на занятие
            enrollmentsService.enrollClient(client.getId(), Integer.parseInt(classId));
        } catch (IllegalStateException e) {
            // Обработка ошибки, если клиент уже записан на это занятие
            return "redirect:/schedule?error=alreadyEnrolled";
        } catch (Exception e) {
            // Общая обработка ошибок
            return "redirect:/schedule?error=unknownError";
        }

        return "redirect:/schedule?success=enrolled";
    }







}
