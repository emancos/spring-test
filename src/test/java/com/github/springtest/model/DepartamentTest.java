package com.github.springtest.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepartamentTest {

    private Departament departament;

    @BeforeEach
    void setup() {
        departament = Departament.builder()
                .id(1L)
                .name("Valid_Name".toUpperCase())
                .build();
    }

    @Test
    public void givenEmployesStringWhenEmployeeToStringThenReturnString () {
        String departamentToString = "Departament(id=1, name=VALID_NAME)";
        Assertions.assertThat(departament.toString()).isEqualTo(departamentToString);
    }
}
