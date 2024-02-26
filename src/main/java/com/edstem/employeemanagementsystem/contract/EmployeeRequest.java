package com.edstem.employeemanagementsystem.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String name;
    @NotEmpty(message = "enter a valid email address")
    @Email(message = "validation error")
    private String email;
    @NotEmpty(message = "enter a valid department")
    private String department;
}
