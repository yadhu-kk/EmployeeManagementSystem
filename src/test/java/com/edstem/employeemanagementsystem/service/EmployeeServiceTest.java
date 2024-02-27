
package com.edstem.employeemanagementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.employeemanagementsystem.contract.EmployeeRequest;
import com.edstem.employeemanagementsystem.contract.EmployeeResponse;
import com.edstem.employeemanagementsystem.model.Employee;
import com.edstem.employeemanagementsystem.repository.EmployeeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.*;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeService.class})
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @MockBean
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
void testAddEmployee(){
    EmployeeRequest request=new EmployeeRequest("yadhu","yadhu@gmail.com","cs");
    Employee employee=modelMapper.map(request,Employee.class);
    EmployeeResponse expectedResponse=modelMapper.map(employee,EmployeeResponse.class);
}

    @Test
    void testGetEmployeeById() {
        Long id = 1L;
        Employee employee = new Employee(id, "yadhu", "yadhu@gmail.com", "cs");
        EmployeeResponse expectedResponse = modelMapper.map(employee, EmployeeResponse.class);
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeById(id));
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        EmployeeResponse actualResponse = employeeService.getEmployeeById(id);
        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    void testGetByDepartment() {
        String department = "cs";
        Employee employeeOne = new Employee(1L, "yadhu", "yadhu@gmail.com", department);
        Employee employeeTwo = new Employee(1L, "prashob", "prashob@gmail.com", department);

        List<Employee> employees = Arrays.asList(employeeOne, employeeTwo);
        List<EmployeeResponse> expectedResponse =
                employees.stream()
                        .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                        .collect(Collectors.toList());

        when(employeeRepository.findByDepartment(department)).thenReturn(Collections.emptyList());
        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.getByDepartment(department));
        when(employeeRepository.findByDepartment(department)).thenReturn(employees);
        List<EmployeeResponse> actualResponse =
                employeeService.getByDepartment(department);
        assertEquals(expectedResponse, actualResponse);
    }
}
