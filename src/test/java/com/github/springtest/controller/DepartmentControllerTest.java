package com.github.springtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtest.model.Department;
import com.github.springtest.service.DepartmentService;
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
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("JUnit test for endpoint create department")
    @Test
    public void givenDepartmentObjectWhenCreateDepartmentThenReturnSavedDepartment() throws Exception {
        Department department = new Department(1L, "IT");
        BDDMockito.given(departmentService.saveDepartment(ArgumentMatchers.any(Department.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department))
        );

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(department.getId()), Long.class))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(department.getName())));
    }

    @DisplayName("JUnit test for get all departments")
    @Test
    public void givenListOfDepartmentsWhenGetAllDepartmentsThenReturnDepartmentsList() throws Exception {
        List<Department> departments = createDepartmentList();
        BDDMockito.given(departmentService.getAllDepartments()).willReturn(departments);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/departments"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(departments.size())));
    }

    @DisplayName("JUnit test for positive scenario get department by id")
    @Test
    public void givenDepartmentIdWhenGetDepartmentByIdThenReturnDepartmentObject() throws Exception {
        Long id = 1L;
        Department department = new Department(id, "IT");
        BDDMockito.given(departmentService.getDepartmentById(id)).willReturn(Optional.of(department));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/departments/{id}", id));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(department.getId()), Long.class))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(department.getName())));
    }

    @DisplayName("JUnit test for negative scenario get department by id")
    @Test
    public void givenInvalidDepartmentIdWhenGetDepartmentByIdThenReturnEmpty() throws Exception {
        Long id = 1L;
        BDDMockito.given(departmentService.getDepartmentById(id)).willReturn(Optional.empty());
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/departments/{id}", id));
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("JUnit test for update department")
    @Test
    public void givenUpdateDepartmentWhenUpdateDepartmentThenReturnUpdateDepartmentObject() throws Exception {
        Long id = 1L;
        Department savedDepartment = new Department(id, "IT");
        Department updatedDepartment = new Department(id, "IT Updated");

        BDDMockito.given(departmentService.getDepartmentById(id)).willReturn(Optional.of(savedDepartment));
        BDDMockito.given(departmentService.updateDepartment(ArgumentMatchers.any(Department.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/departments/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDepartment)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(updatedDepartment.getId()), Long.class))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(updatedDepartment.getName())));
    }

    private static List<Department> createDepartmentList() {
        List<Department> departmentList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            departmentList.add(new Department((long) i, "Department " + i));
        }
        return departmentList;
    }
}
