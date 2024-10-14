package com.example.fitlove.controllers;

import com.example.fitlove.models.GroupClasses;
import com.example.fitlove.services.GroupClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/classes")
public class GroupClassesController {

    private final GroupClassesService groupClassesService;

    @Autowired
    public GroupClassesController(GroupClassesService groupClassesService) {
        this.groupClassesService = groupClassesService;
    }

    // Получение всех групповых занятий
    @GetMapping
    public String listClasses(Model model) {
        List<GroupClasses> classes = groupClassesService.getAllGroupClasses();
        model.addAttribute("classes", classes);
        return "class/list"; // Возвращает представление со списком групповых занятий
    }

    // Получение формы для добавления нового занятия
    @GetMapping("/new")
    public String createClassForm(Model model) {
        model.addAttribute("groupClass", new GroupClasses());
        return "class/create"; // Возвращает представление для создания занятия
    }

    // Обработка сохранения нового занятия
    @PostMapping
    public String saveClass(@ModelAttribute GroupClasses groupClass) {
        groupClassesService.saveGroupClass(groupClass);
        return "redirect:/classes"; // Перенаправление на список групповых занятий
    }

    // Получение формы для редактирования занятия
    @GetMapping("/{id}/edit")
    public String editClassForm(@PathVariable int id, Model model) {
        GroupClasses groupClass = groupClassesService.getGroupClassById(id)
                .orElseThrow(() -> new RuntimeException("Group class not found with id " + id));
        model.addAttribute("groupClass", groupClass);
        return "class/edit"; // Возвращает представление для редактирования занятия
    }

    // Обработка обновления занятия
    @PostMapping("/{id}")
    public String updateClass(@PathVariable int id, @ModelAttribute GroupClasses groupClass) {
        groupClass.setId(id); // Устанавливаем ID занятия перед обновлением
        groupClassesService.updateGroupClass(groupClass);
        return "redirect:/classes"; // Перенаправление на список групповых занятий
    }

    // Обработка удаления занятия
    @PostMapping("/{id}/delete")
    public String deleteClass(@PathVariable int id) {
        groupClassesService.deleteGroupClass(id);
        return "redirect:/classes"; // Перенаправление на список групповых занятий
    }
}
