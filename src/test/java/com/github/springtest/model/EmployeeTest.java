package com.github.springtest.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    public static final String FIRST_NAME = "Valid_Name_Employee";
    public static final String LAST_NAME = "Valid_Last_Name_Employee";
    public static final String EMAIL = "valid_email_employee@mail.com";

    @Test
    public void givenEmployeeWhenEmployeeConstructorThenReturnEmployee() {
        Long id = 1L;
        String firstName = FIRST_NAME;
        String lastName = LAST_NAME;
        String email = EMAIL;
        Department department = new Department(1L, "IT");

        Employee employee = new Employee(id, firstName, lastName, email, department);

        assertEquals(id, employee.getId());
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(email, employee.getEmail());
        assertEquals(department, employee.getDepartment());
    }

    @Test
    public void givenEmployesPropertiesWhenEmployeeGettersSettersThenReturnEmployeeProperties() {
        Employee employee = new Employee();

        Long id = 1L;
        String firstName = FIRST_NAME;
        String lastName = LAST_NAME;
        String email = EMAIL;
        Department department = new Department(1L, "IT");

        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setDepartment(department);

        assertEquals(id, employee.getId());
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(email, employee.getEmail());
        assertEquals(department, employee.getDepartment());
    }

    @Test
    public void givenEmployesStringWhenEmployeeToStringThenReturnString() {
        Long id = 1L;
        Department department = new Department(1L, "IT");

        Employee employee = new Employee(id, FIRST_NAME, LAST_NAME, EMAIL, department);

        String expectedToString = String.format(
                "Employee(id=1, firstName=%s, lastName=%s, email=%s, department=Department(id=1, name=IT))",
                FIRST_NAME,
                LAST_NAME,
                EMAIL
        );
        assertEquals(expectedToString, employee.toString());
    }
}