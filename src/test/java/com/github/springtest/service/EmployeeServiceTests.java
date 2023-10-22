package com.github.springtest.service;

import com.github.springtest.exception.ResourceNotFoundException;
import com.github.springtest.model.Employee;
import com.github.springtest.repository.EmployeeRepository;
import com.github.springtest.service.impl.EmployeeServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Valid_Name_Employee")
                .lastName("Valid_Last_Name_Employee")
                .email("valid_email_employee@mail.com")
                .build();
    }

    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeWhenSaveEmployeeThenReturnEmployeeObject() {
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        Employee savedEmployee = employeeService.saveEmployee(employee);
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for saveEmployee method with throws exeption")
    @Test
    public void givenExistingEmailWhenSaveEmployeeThenThrowsException() {
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeesListWhenGetAllEmployeeThenReturnEmployeesList() {
        Employee employee1 = Employee.builder()
                .firstName("Valid_Name_Employee1")
                .lastName("Valid_Last_Name_Employee1")
                .email("valid_email_employee1@mail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
        List<Employee> employeeList = employeeService.getAllEmployees();
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllEmployees method with empty list")
    @Test
    public void givenEmptyEmployeesListWhenGetAllEmployeeThenReturnEmptyEmployeesList() {
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        List<Employee> employeeList = employeeService.getAllEmployees();
        assertThat(employeeList).isEmpty();
    }

    @DisplayName("JUnit test for getEmployeesById method")
    @Test
    public void givenEmployeesIdWhenGetEmployeeByIdThenReturnEmployeesObject() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).orElse(null);
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeesObjectWhenUpdateEmployeeThenReturnEmployeesObject() {
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setFirstName("Valid_FisrtName_Employee_Updated");
        employee.setLastName("Valid_LastName_Employee_Updated");
        employee.setEmail("valid_email_employee_updated@mail.com");
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Valid_FisrtName_Employee_Updated");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Valid_LastName_Employee_Updated");
        assertThat(updatedEmployee.getEmail()).isEqualTo("valid_email_employee_updated@mail.com");
    }

    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeesIdWhenDeleteEmployeeThenNothing() {
        Long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);
        employeeService.deleteEmployee(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
