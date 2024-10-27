package com.example.fitlove.repositories;

import com.example.fitlove.models.GroupClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface GroupClassesRepository extends JpaRepository<GroupClasses, Integer> {
    // Здесь можно добавить дополнительные методы, если необходимо


}