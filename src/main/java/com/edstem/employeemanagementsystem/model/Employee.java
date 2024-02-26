package com.edstem.employeemanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_name",nullable = false,length = 150)
    @NotEmpty(message="please enter your name")
    private String name;
    @NotEmpty(message = "enter a valid email address")
    @Email(message = "validation error")
    private String email;
    @NotEmpty(message = "enter a valid department")
    private String department;

}
