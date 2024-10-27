package com.example.fitlove.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void enrollClient(int clientId, int classId) {
        // Проверяем, существует ли уже такая запись
        String checkSql = "SELECT COUNT(*) FROM Enrollments WHERE client_id = ? AND class_id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, clientId, classId);

        if (count != null && count == 0) {
            // Если записи нет, добавляем новую
            String insertSql = "INSERT INTO Enrollments (client_id, class_id) VALUES (?, ?)";
            jdbcTemplate.update(insertSql, clientId, classId);
        } else {
            throw new IllegalStateException("Client is already enrolled in this class.");
        }
    }
}