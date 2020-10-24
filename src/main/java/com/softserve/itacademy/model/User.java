package com.softserve.itacademy.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "task_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Pattern(regexp = "[A-Z][a-z]+(-[A-Z][a-z]+)?")
    @NotNull
    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+(-[A-Z][a-z]+)?")
    @NotNull
    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Pattern(regexp = "[1-9A-Za-z!%@*]{8,20}", message = "Invalid password!")
    @NotBlank(message = "Can't be empty")
    @Column(nullable = false, name = "password")
    private String password;

    @NotNull(message = "Role can not be null!")
    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<ToDo> toDoList;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

