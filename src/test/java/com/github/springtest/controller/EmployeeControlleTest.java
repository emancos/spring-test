package com.github.springtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtest.model.Department;
import com.github.springtest.model.Employee;
import com.github.springtest.service.DepartmentService;
import com.github.springtest.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControlleTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private DepartmentService departmentService;
    @Autowired
    private ObjectMapper objectMapper;

    private Department department;


    @BeforeEach
    void setup() {
        department = Department.builder()
                .id(1L)
                .name("IT")
                .build();
    }

    @DisplayName("JUnit test for endpoint create employee")
    @Test
    public void givenEmployeeObjectWhenCreateEmployeeThenReturnSavedEmployee() throws Exception {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Valid_Name_Employee")
                .lastName("Valid_Last_Name_Employee")
                .email("valid_email_employee@mail.com")
                .build();
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    @DisplayName("JUnit test for get all employees")
    @Test
    public void givenListOfEmployessWhenGetAllEmployeesThenReturnEmployeesList() throws Exception {
        List<Employee> employees = createEmployeeList(department);
        given(employeeService.getAllEmployees()).willReturn(employees);
        ResultActions response = mockMvc.perform(get("/api/v1/employees"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(employees.size())));
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
        given(employeeService.getEmployeeById(id)).willReturn(Optional.of(employee));
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", id));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", CoreMatchers.is(employee.getId()), Long.class))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @DisplayName("JUnit test for negative scenario get employee by id")
    @Test
    public void givenInvalidEmployeeIdWhenGetEmployeeByIdThenReturnEmpty() throws Exception {
        Long id = 1L;
        given(employeeService.getEmployeeById(id)).willReturn(Optional.empty());
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", id));
        response.andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("JUnit test for update employee")
    @Test
    public void givenUpdateEmployeeWhenUpdateEmployeeThenReturnUpdateEmployeeObject() throws Exception {
         Long id = 1L;
         Employee savedEmployee = Employee.builder()
                .id(id)
                .firstName("Saved_Name_Employee")
                .lastName("Saved_Last_Name_Employee")
                .email("saved_email_employee@mail.com")
                .build();

         Employee updatedEmployee = Employee.builder()
                .id(id)
                .firstName("Updated_Name_Employee")
                .lastName("Updated_Last_Name_Employee")
                .email("updated_email_employee@mail.com")
                .build();

         given(employeeService.getEmployeeById(id)).willReturn(Optional.of(savedEmployee));
         given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
         ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", id)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(updatedEmployee)));
         response.andExpect(status().isOk())
                 .andDo(print());
    }

     @DisplayName("JUnit test for get employees by department")
    @Test
    public void givenDepartmentIdWhenGetEmployeesByDepartmentThenReturnEmployeesList() throws Exception {
        List<Employee> employees = createEmployeeList(department);
        given(employeeService.getEmployeesByDepartment(department.getName())).willReturn(employees);
        ResultActions response = mockMvc
                .perform(get("/api/v1/employees/department/{name}", department.getName()));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(employees.size())))
                .andDo(print());
    }

    private static List<Employee> createEmployeeList(Department department) {
        List<Employee> employeeList = new ArrayList<>();
        for(int i = 1; i < 6; i++) {
            employeeList.add(
                Employee.builder()
                        .id((long) i)
                        .firstName(i+" - Valid_Name_Employee")
                        .lastName(i+" - Valid_Last_Name_Employee")
                        .email(i+"valid_email_employee@mail.com")
                        .department(department)
                        .build()
            );
        }
        return employeeList;
    }
}
