package com.github.springtest.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

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

    @Test
    public void givenEmployesString_whenEmployeeToString_thenReturnString () {
        String employeeToString = "Employee(id=1, firstName=Valid_Name_Employee, lastName=Valid_Last_Name_Employee, email=valid_email_employee@mail.com)";
        Assertions.assertThat(employee.toString()).isEqualTo(employeeToString);
    }
}
