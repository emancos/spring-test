package com.github.springtest.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepartmentTest {

    private Department department;

    @BeforeEach
    void setup() {
        department = Department.builder()
                .id(1L)
                .name("Valid_Name".toUpperCase())
                .build();
    }

    @Test
    public void givenEmployesStringWhenDepartmentToStringThenReturnString () {
        String departmentToString = "Department(id=1, name=VALID_NAME)";
        Assertions.assertThat(department.toString()).isEqualTo(departmentToString);
    }
}
