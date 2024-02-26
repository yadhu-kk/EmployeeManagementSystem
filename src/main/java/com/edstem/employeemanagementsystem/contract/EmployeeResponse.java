package com.edstem.employeemanagementsystem.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private String department;
}
