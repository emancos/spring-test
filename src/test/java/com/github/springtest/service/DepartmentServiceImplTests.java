package com.github.springtest.service;

import com.github.springtest.exception.ResourceNotFoundException;
import com.github.springtest.model.Department;
import com.github.springtest.model.Employee;
import com.github.springtest.repository.DepartmentRepository;
import com.github.springtest.repository.EmployeeRepository;
import com.github.springtest.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTests {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private DepartmentServiceImpl departmentService;
    private Department department;

    @BeforeEach
    public void setup() {
        department = Department.builder()
                .id(1L)
                .name("Valid_Name_Department")
                .build();
    }

    @DisplayName("JUnit test for saveDepartment method")
    @Test
    public void givenDepartmentWhenSaveDepartmentThenReturnDepartmentObject() {
        given(departmentRepository.findByName(department.getName())).willReturn(Optional.empty());
        given(departmentRepository.save(department)).willReturn(department);
        Department savedDepartment = departmentService.saveDepartment(department);
        assertThat(savedDepartment).isNotNull();
    }

    @DisplayName("JUnit test for saveDepartment method with throws exeption")
    @Test
    public void givenExistingNameWhenSaveDepartmentThenThrowsException() {
        given(departmentRepository.findByName(department.getName())).willReturn(Optional.of(department));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> departmentService.saveDepartment(department));
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @DisplayName("JUnit test for getAllDepartments method")
    @Test
    public void givenDepartmentsListWhenGetAllDepartmentsThenReturnDepartmentsList() {
        Department department1 = Department.builder()
                .name("Valid_Name_Department1")
                .build();
        given(departmentRepository.findAll()).willReturn(List.of(department, department1));
        List<Department> departmentList = departmentService.getAllDepartments();
        assertThat(departmentList).isNotNull();
        assertThat(departmentList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllDepartments method with empty list")
    @Test
    public void givenEmptyDepartmentsListWhenGetAllDepartmentsThenReturnEmptyDepartmentsList() {
        given(departmentRepository.findAll()).willReturn(Collections.emptyList());
        List<Department> departmentList = departmentService.getAllDepartments();
        assertThat(departmentList).isEmpty();
    }

    @DisplayName("JUnit test for getDepartmentById method")
    @Test
    public void givenDepartmentIdWhenGetDepartmentByIdThenReturnDepartmentObject() {
        given(departmentRepository.findById(1L)).willReturn(Optional.of(department));
        Department savedDepartment = departmentService.getDepartmentById(department.getId()).orElse(null);
        assertThat(savedDepartment).isNotNull();
    }

    @DisplayName("JUnit test for updateDepartment method")
    @Test
    public void givenDepartmentObjectWhenUpdateDepartmentThenReturnDepartmentObject() {
        given(departmentRepository.save(department)).willReturn(department);
        department.setName("Valid_Name_Department_Updated");
        Department updatedDepartment = departmentService.updateDepartment(department);
        assertThat(updatedDepartment.getName()).isEqualTo("Valid_Name_Department_Updated");
    }

    @DisplayName("JUnit test for deleteDepartment method")
    @Test
    public void givenDepartmentIdWhenDeleteDepartmentThenNothing() {
        Long departmentId = 1L;
        willDoNothing().given(departmentRepository).deleteById(departmentId);
        departmentService.deleteDepartment(departmentId);
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }

    @DisplayName("JUnit test for get Employees by Department")
    @Test
    public void givenDepartmentWhenGetEmployeesByDepartmentThenReturnEmployeesList() {
        Long departmentId = 1L;
        Department department = Department.builder()
                .id(departmentId)
                .name("IT")
                .build();

        List<Employee> expectedEmployees = createEmployeeList();
        given(employeeRepository.findEmployeesByDepartment(department.getId()))
                .willReturn(expectedEmployees);
        List<Employee> resultEmployees = departmentService.getEmployeesByDepartment(department.getId());
        assertThat(resultEmployees).isNotNull();
        assertThat(resultEmployees.size()).isEqualTo(expectedEmployees.size());
    }

    private static List<Employee> createEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            employeeList.add(
                    Employee.builder()
                            .id((long) i)
                            .firstName("Employee" + i)
                            .lastName("Lastname" + i)
                            .email("employee" + i + "@example.com")
                            .build()
            );
        }
        return employeeList;
    }
}
