package com.github.springtest.repository;

import com.github.springtest.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    public static final String VALID_NAME_EMPLOYEE = "Valid_Name_Employee";
    public static final String VALID_LAST_NAME_EMPLOYEE = "Valid_Last_Name_Employee";
    public static final String VALID_EMAIL_EMPLOYEE = "valid_email_employee@mail.com";
    private Employee employee;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName(VALID_NAME_EMPLOYEE)
                .lastName(VALID_LAST_NAME_EMPLOYEE)
                .email(VALID_EMAIL_EMPLOYEE)
                .build();
    }

    @DisplayName("JUnit test for save employee operation")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        Employee employeeSaved = employeeRepository.save(employee);
        assertThat(employeeSaved).isNotNull();
        assertThat(employeeSaved.getId()).isPositive();
    }

    @DisplayName("JUnit test for get all employees operation")
    @Test
    void givenEmployeeList_whenFindAll_thenEmployeeList() {
        int listSize = 5;
        for (int i = 0; i < listSize; i++) {
            Employee aEmployee = Employee.builder()
                .firstName(VALID_NAME_EMPLOYEE + i)
                .lastName(VALID_LAST_NAME_EMPLOYEE + i)
                .email("valid_email_employee"+ i + "@mail.com")
                .build();
            employeeRepository.save(aEmployee);
        }
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(listSize).isNotNull();
    }

    @DisplayName("JUnit test for get employee by id operation")
    @Test
    void givenEmplyeeObject_whenFindById_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeById = employeeRepository.findById(employee.getId()).orElse(null);
        assertThat(employeeById).isNotNull();
    }

    @DisplayName("JUnit test for get employee by email operation")
    @Test
    void givenEmplyeeObject_whenFindByEmail_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByEmail = employeeRepository.findByEmail(employee.getEmail()).orElse(null);
        assertThat(employeeByEmail).isNotNull();
    }

    @DisplayName("JUnit test for update employee operation")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        assertThat(savedEmployee).isNotNull();
        savedEmployee.setFirstName("Valid_Name_Employee_Updated");
        savedEmployee.setLastName("Valid_Last_Name_Employee_Updated");
        savedEmployee.setEmail("valid_email_employee_updated@mail.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Valid_Name_Employee_Updated");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Valid_Last_Name_Employee_Updated");
        assertThat(updatedEmployee.getEmail()).isEqualTo("valid_email_employee_updated@mail.com");

    }

    @DisplayName("JUnit test for delete employee operation")
    @Test
    void givenEmplyeeObject_whenDeleteEmployee_thenRemoveEmployee() {
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        assertThat(deletedEmployee).isEmpty();
    }

    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository
                .findByJPQL(VALID_NAME_EMPLOYEE, VALID_LAST_NAME_EMPLOYEE);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for custom query using JPQL with named params")
    @Test
    void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository
                .findByJPQLNamedParams(VALID_NAME_EMPLOYEE, VALID_LAST_NAME_EMPLOYEE);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for native query using native SQL with index")
    @Test
    void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository
                .findByNativeSQL(VALID_NAME_EMPLOYEE, VALID_LAST_NAME_EMPLOYEE);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for native query using native SQL with named params")
    @Test
    void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository
                .findByNativeSQLNamedParams(VALID_NAME_EMPLOYEE, VALID_LAST_NAME_EMPLOYEE);
        assertThat(employeeByJPQL).isNotNull();
    }
}
