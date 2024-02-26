package com.edstem.employeemanagementsystem.controller;

import com.edstem.employeemanagementsystem.contract.EmployeeRequest;
import com.edstem.employeemanagementsystem.contract.EmployeeResponse;
import com.edstem.employeemanagementsystem.model.Employee;
import com.edstem.employeemanagementsystem.repository.EmployeeRepository;
import com.edstem.employeemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    @PostMapping
    public EmployeeResponse addEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        return employeeService.addEmployee(employeeRequest);
    }
    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }
    @GetMapping("/department/{department}")
public List<EmployeeResponse>getEmployeeByDepartment(@PathVariable String department){
        return employeeService.getByDepartment(department);
}
}
