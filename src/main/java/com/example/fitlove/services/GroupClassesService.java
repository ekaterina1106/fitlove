package com.example.fitlove.services;

import com.example.fitlove.models.GroupClasses;
import com.example.fitlove.repositories.GroupClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
}

