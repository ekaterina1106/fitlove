package com.example.fitlove.services;

import com.example.fitlove.models.Instructors;
import com.example.fitlove.repositories.InstructorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class InstructorsService {
    private final InstructorsRepository instructorsRepository;

    @Autowired
    public InstructorsService(InstructorsRepository instructorsRepository) {
        this.instructorsRepository = instructorsRepository;
    }

    public List<Instructors> getAllInstructors() {
        return instructorsRepository.findAll();
    }

    public Optional<Instructors> getInstructorById(int id) {
        return instructorsRepository.findById(id);
    }

    @Transactional
    public Instructors saveInstructor(Instructors instructor) {
        return instructorsRepository.save(instructor);
    }

    @Transactional
    public Instructors updateInstructor(Instructors instructor) {
        return instructorsRepository.findById(instructor.getId())
                .map(existingInstructor -> {
                    existingInstructor.setName(instructor.getName());
                    // Здесь вы можете обновить другие поля, если необходимо
                    return instructorsRepository.save(existingInstructor);
                })
                .orElseThrow(() -> new RuntimeException("Instructor not found with id " + instructor.getId()));
    }

    @Transactional
    public void deleteInstructor(int instructorId) {
        instructorsRepository.deleteById(instructorId);
    }
}
