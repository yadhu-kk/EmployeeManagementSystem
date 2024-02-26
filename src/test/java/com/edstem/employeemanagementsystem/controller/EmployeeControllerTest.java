package com.edstem.employeemanagementsystem.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.edstem.employeemanagementsystem.contract.EmployeeRequest;
import com.edstem.employeemanagementsystem.contract.EmployeeResponse;
import com.edstem.employeemanagementsystem.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

@ContextConfiguration(classes = {EmployeeController.class})
@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;
    @Test
    void testAddEmployee() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/employee")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new EmployeeRequest()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
    @Test
    void testGetEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(anyLong())).thenReturn(new EmployeeResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/{id}", 1L);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":null,\"name\":null,\"email\":null,\"department\":null}"));
    }
    @Test
    void testGetEmployeeByDepartment() throws Exception {
        when(employeeService.getByDepartment(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/department/{department}",
                "Department");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}