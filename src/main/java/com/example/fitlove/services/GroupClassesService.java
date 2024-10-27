package com.example.fitlove.services;

import com.example.fitlove.models.GroupClasses;
import com.example.fitlove.repositories.GroupClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GroupClassesService {
    private final GroupClassesRepository groupClassesRepository;

    @Autowired
    public GroupClassesService(GroupClassesRepository groupClassesRepository) {
        this.groupClassesRepository = groupClassesRepository;
    }

    public List<GroupClasses> getAllGroupClasses() {
        return groupClassesRepository.findAll();
    }

    public Optional<GroupClasses> getGroupClassById(int id) {
        return groupClassesRepository.findById(id);
    }

    @Transactional
    public GroupClasses saveGroupClass(GroupClasses groupClass) {
        return groupClassesRepository.save(groupClass);
    }

    @Transactional
    public GroupClasses updateGroupClass(GroupClasses groupClass) {
        return groupClassesRepository.findById(groupClass.getId())
                .map(existingGroupClass -> {
                    existingGroupClass.setName(groupClass.getName());
                    existingGroupClass.setDescription(groupClass.getDescription());
                    existingGroupClass.setClassDate(groupClass.getClassDate());
                    existingGroupClass.setStartTime(groupClass.getStartTime());
                    existingGroupClass.setInstructor(groupClass.getInstructor());
                    return groupClassesRepository.save(existingGroupClass);
                })
                .orElseThrow(() -> new RuntimeException("Group class not found with id " + groupClass.getId()));
    }

    @Transactional
    public void deleteGroupClass(int groupClassId) {
        groupClassesRepository.deleteById(groupClassId);
    }

    public List<LocalDate> getWeekDates() {
        // Возвращаем список дат на неделю начиная с сегодняшнего дня
        LocalDate startDate = LocalDate.now();
        return List.of(
                startDate,
                startDate.plusDays(1),
                startDate.plusDays(2),
                startDate.plusDays(3),
                startDate.plusDays(4),
                startDate.plusDays(5),
                startDate.plusDays(6)
        );
    }

    public List<String> getWeekDays(List<LocalDate> weekDates) {
        Locale russianLocale = new Locale("ru"); // Устанавливаем локаль на русский
        return weekDates.stream()
                .map(date -> date.getDayOfWeek().getDisplayName(TextStyle.FULL, russianLocale).toLowerCase()) // Получаем название дня недели на русском в нижнем регистре
                .collect(Collectors.toList());
    }


    public List<LocalTime> getTimes() {
        // Временные интервалы занятий (пример)
        return List.of(
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0)
        );
    }

}




