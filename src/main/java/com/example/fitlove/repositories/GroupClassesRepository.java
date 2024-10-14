package com.example.fitlove.repositories;

import com.example.fitlove.models.GroupClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupClassesRepository extends JpaRepository<GroupClasses, Integer> {
    // Здесь можно добавить дополнительные методы, если необходимо
}