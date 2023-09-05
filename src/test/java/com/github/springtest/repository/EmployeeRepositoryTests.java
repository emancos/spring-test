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

    private Employee employee;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("Valid_Name_Employee")
                .lastName("Valid_Last_Name_Employee")
                .email("valid_email_employee@mail.com")
                .build();
    }

    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        Employee employeeSaved = employeeRepository.save(employee);
        assertThat(employeeSaved).isNotNull();
        assertThat(employeeSaved.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        int listSize = 5;
        for (int i = 0; i < listSize; i++) {
            Employee employee1 = Employee.builder()
                .firstName("Valid_Name_Employee" + i)
                .lastName("Valid_Last_Name_Employee" + i)
                .email("valid_email_employee"+ i + "@mail.com")
                .build();
            employeeRepository.save(employee1);
        }
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(listSize);
    }

    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmplyeeObject_whenFindById_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeById = employeeRepository.findById(employee.getId()).orElse(null);
        assertThat(employeeById).isNotNull();
    }

    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmplyeeObject_whenFindByEmail_thenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByEmail = employeeRepository.findByEmail(employee.getEmail()).orElse(null);
        assertThat(employeeByEmail).isNotNull();
    }

    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
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
    public void givenEmplyeeObject_whenDeleteEmployee_thenRemoveEmployee() {
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        assertThat(deletedEmployee).isEmpty();
    }

    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        String firstName = "Valid_Name_Employee";
        String lastName = "Valid_Last_Name_Employee";
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository.findByJPQL(firstName, lastName);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for custom query using JPQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        String firstName = "Valid_Name_Employee";
        String lastName = "Valid_Last_Name_Employee";
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository.findByJPQLNamedParams(firstName, lastName);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for native query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        String firstName = "Valid_Name_Employee";
        String lastName = "Valid_Last_Name_Employee";
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository.findByNativeSQL(firstName, lastName);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for native query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        String firstName = "Valid_Name_Employee";
        String lastName = "Valid_Last_Name_Employee";
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository.findByNativeSQLNamedParams(firstName, lastName);
        assertThat(employeeByJPQL).isNotNull();
    }
}
