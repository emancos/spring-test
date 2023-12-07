package com.github.springtest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtest.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


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
    public void givenDepartamentObjectWhenSaveDepartmentThenReturnSavedDepartament() {
        Department savedDepartment = repository.save(department);
        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getId()).isPositive();
        assertThat(savedDepartment.getName()).isEqualTo(NAME);
    }

    @DisplayName("JUnit test for get all departments operation")
    @Test
    public void givenDepartmentListWhenFindAllThenDepartmentList() {
        int listSize = 5;
        getListDepartment(listSize);
        List<Department> departments = repository.findAll();
        assertThat(departments).hasSize(listSize).isNotNull();
    }

    @DisplayName("JUnit test for get department by id operation")
    @Test
    public void givenDepartmentObjectWhenFindByIdThenReturnDepartmentObject() {
        Department savedDepartment = repository.save(department);
        Department departmentById = repository.findById(savedDepartment.getId()).orElse(null);
        assertThat(departmentById).isNotNull();
    }

    @DisplayName("JUnit test for get department by name operation")
    @Test
    public void givenDepartmentObjectWhenFindByNameThenReturnDepartmentObject() {
        repository.save(department);
        Department departmentById = repository.findByName(NAME).orElse(null);
        assertThat(departmentById).isNotNull();
    }

    @DisplayName("JUnit test for update department operation")
    @Test
    public void givenDepartmentObjectWhenUpdateDepartmentThenReturnUpdatedDepartment() {
        var aDepartment = repository.save(department);
        Department savedDepartment = repository.findById(aDepartment.getId()).orElse(null);
        assertThat(savedDepartment).isNotNull();
        savedDepartment.setName("VALID_NAME_UPDATED");
        Department updatedDepartment = repository.save(savedDepartment);
        assertThat(updatedDepartment).isNotNull();
        assertThat(updatedDepartment.getName()).isEqualTo("VALID_NAME_UPDATED");
    }

    @DisplayName("JUnit test for delete department operation")
    @Test
    void givenDepartmentObjectWhenDeleteDepartmentThenRemoveDepartment() {
        var aDepartiment = repository.save(department);
        repository.deleteById(aDepartiment.getId());
        Optional<Department> deletedDepartment = repository.findById(aDepartiment.getId());
        assertThat(deletedDepartment).isEmpty();
    }

    private void getListDepartment(int listSize) {
        for(int i = 0; i < listSize; i++) {
            Department aDepartment = Department.builder()
                    .name(NAME + "_" + (i+1))
                    .build();
            repository.save(aDepartment);
        }

    }
}
