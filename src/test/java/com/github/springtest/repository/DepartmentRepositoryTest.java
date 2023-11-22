package com.github.springtest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtest.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DepartmentRepositoryTest {

    private static final Long ID = 1L;
    private static final String NAME = "VALID_NAME";

    private Department department;

    @Autowired
    private DepartmentRepository repository;

    @BeforeEach
    public void setup() {
        department = Department.builder()
                .id(ID)
                .name(NAME)
                .build();
    }

    @DisplayName("JUnit test for save department operation")
    @Test
    void givenDepartamentObjectWhenSaveDepartmentThenReturnSavedDepartament() {
        Department savedDepartment = repository.save(department);
        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getId()).isPositive();
        assertThat(savedDepartment.getName()).isEqualTo(NAME);
    }
}
