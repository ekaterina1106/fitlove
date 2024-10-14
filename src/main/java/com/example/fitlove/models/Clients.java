package com.example.fitlove.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Clients")
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 60, message = "Имя не должно превышать 60 символов")
    private String name;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email")
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 4, message = "Пароль должен быть не менее 4 символов")
    private String password;


    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Роль обязательна")
    private Role role;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "Enrollments",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "class_id", referencedColumnName="id"))
    private Set<GroupClasses> classes = new HashSet<>();

    public Clients() {
    }

    public enum Role {
        USER, ADMIN // Пример значений
    }
//    public Clients(String name, String email, String password, String phone, String role) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.phone = phone;
//        this.role = role;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
