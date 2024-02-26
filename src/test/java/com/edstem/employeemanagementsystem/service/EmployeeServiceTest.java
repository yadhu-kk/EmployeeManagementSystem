
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

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

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
        Employee employee = new Employee();
        employee.setDepartment("cs");
        employee.setEmail("yadhu@gmail.com");
        employee.setId(1L);
        employee.setName("yadhu");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<EmployeeResponse>>any())).thenReturn(employeeResponse);
        EmployeeResponse actualEmployeeById = employeeService.getEmployeeById(1L);
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<EmployeeResponse>>any());
        verify(employeeRepository).findById(Mockito.<Long>any());
        assertSame(employeeResponse, actualEmployeeById);
    }
    @Test
    void testGetByDepartment() {
        Employee employee = new Employee();
        employee.setDepartment("Department not found");
        employee.setEmail("yadhu@gmail.com");
        employee.setId(1L);
        employee.setName("Department not found");

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeRepository.findByDepartment(Mockito.<String>any())).thenReturn(employeeList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<EmployeeResponse>>any()))
                .thenThrow(new EntityExistsException("An error occurred"));
        assertThrows(EntityExistsException.class, () -> employeeService.getByDepartment("Department"));
        verify(employeeRepository).findByDepartment(Mockito.<String>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<EmployeeResponse>>any());
    }
}
