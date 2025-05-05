package com.javarush.javarushfinal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users",schema = "auto_parts")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotEmpty(message = "Ім'я не може бути пустим")
    private String name;
    @Column(nullable = false)
    @NotEmpty(message = "Прізвище не може бути пустим")
    private String lastName;
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Логін не може бути пустим")
    @Size(min = 4, max = 20, message = "Логін має бути від 4 до 20 символів")
    private String userName;
    @Column(nullable = false)
    @NotEmpty(message = "Пароль не може бути пустим")
    @Size(min = 6, message = "Пароль має містити мінімум 6 символів")
    private String password;
    @Column(nullable = false)
    @NotNull(message = "Телефон не може бути пустим")
    @Pattern(regexp = "\\d{10,12}", message = "Телефон має містити від 10 до 12 цифр")
    private String phone;
    @Column(nullable = false)
    @Email(message = "Неправильний формат email")
    @NotEmpty(message = "Email не може бути пустим")
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public User setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }
}
