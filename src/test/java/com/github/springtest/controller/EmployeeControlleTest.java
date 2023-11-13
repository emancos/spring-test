package com.github.springtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtest.model.Employee;
import com.github.springtest.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControlleTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("JUnit test for endpoint create employee")
    @Test
    public void givenEmployeeObjectWhenCreateEmployeeThenReturnSavedEmployee() throws Exception {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Valid_Name_Employee")
                .lastName("Valid_Last_Name_Employee")
                .email("valid_email_employee@mail.com")
                .build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    @DisplayName("JUnit test for get all employees")
    @Test
    public void givenListOfEmployessWhenGetAllEmployeesThenReturnEmployeesList() throws Exception {
        List<Employee> employees = createEmployeeList();
        BDDMockito.given(employeeService.getAllEmployees()).willReturn(employees);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employees.size())));
    }

    @DisplayName("JUnit test for positive scenario get employee by id")
    @Test
    public void givenEmployeeIdWhenGetEmployeeByIdThenReturnEmployeeObject() throws Exception {
        Long id = 1L;
        Employee employee = Employee.builder()
                .id(id)
                .firstName("Valid_Name_Employee")
                .lastName("Valid_Last_Name_Employee")
                .email("valid_email_employee@mail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(id)).willReturn(Optional.of(employee));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/{id}", id));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(employee.getId()), Long.class))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @DisplayName("JUnit test for negative scenario get employee by id")
    @Test
    public void givenInvalidEmployeeIdWhenGetEmployeeByIdThenReturnEmpty() throws Exception {
        Long id = 1L;
        BDDMockito.given(employeeService.getEmployeeById(id)).willReturn(Optional.empty());
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/{id}", id));
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    private static List<Employee> createEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        for(int i = 1; i < 6; i++) {
            employeeList.add(
                Employee.builder()
                        .firstName(i+" - Valid_Name_Employee")
                        .lastName(i+" - Valid_Last_Name_Employee")
                        .email(i+"valid_email_employee@mail.com")
                        .build()
            );
        }
        return employeeList;
    }
}
