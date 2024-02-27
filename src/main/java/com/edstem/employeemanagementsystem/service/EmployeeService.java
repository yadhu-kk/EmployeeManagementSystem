package com.edstem.employeemanagementsystem.service;

import com.edstem.employeemanagementsystem.contract.EmployeeRequest;
import com.edstem.employeemanagementsystem.contract.EmployeeResponse;
import com.edstem.employeemanagementsystem.model.Employee;
import com.edstem.employeemanagementsystem.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = modelMapper.map(employeeRequest, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeResponse.class);
    }

    public EmployeeResponse getEmployeeById(long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found" + id
        ));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> getByDepartment(String department) {
        List<Employee> employees = employeeRepository.findByDepartment(department);
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("Department not found");
        }
        return employees.stream().map(item -> modelMapper.map(item, EmployeeResponse.class)).collect(Collectors.toList());
    }
}
