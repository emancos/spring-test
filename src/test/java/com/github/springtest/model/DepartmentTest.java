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

    @Test
    public void givenTwoEqualDepartmentsWhenEqualsThenReturnTrue() {
        Department department1 = Department.builder().id(1L).name("Valid_Name").build();
        Department department2 = Department.builder().id(1L).name("Valid_Name").build();
        Assertions.assertThat(department1).isEqualTo(department2);
    }

    @Test
    public void givenTwoDifferentDepartmentsWhenEqualsThenReturnFalse() {
        Department department1 = Department.builder().id(1L).name("Valid_Name").build();
        Department department2 = Department.builder().id(2L).name("Different_Name").build();
        Assertions.assertThat(department1).isNotEqualTo(department2);
    }

    @Test
    public void givenNoArgumentsWhenDepartmentConstructorThenReturnValidObject() {
        Department emptyDepartment = new Department();
        Assertions.assertThat(emptyDepartment).isNotNull();
    }

    @Test
    public void givenNewNameWhenSetNameThenNameIsUpdated() {
        String newName = "New_Name";
        department.setName(newName);
        Assertions.assertThat(department.getName()).isEqualTo(newName);
    }

    @Test
    public void givenTwoEqualDepartmentsWhenHashCodeThenReturnSameValue() {
        Department department1 = Department.builder().id(1L).name("Valid_Name").build();
        Department department2 = Department.builder().id(1L).name("Valid_Name").build();
        Assertions.assertThat(department1.hashCode()).isEqualTo(department2.hashCode());
    }

    @Test
    public void givenTwoDifferentDepartmentsWhenHashCodeThenReturnDifferentValue() {
        Department department1 = Department.builder().id(1L).name("Valid_Name").build();
        Department department2 = Department.builder().id(2L).name("Different_Name").build();
        Assertions.assertThat(department1.hashCode()).isNotEqualTo(department2.hashCode());
    }

    @Test
    public void givenDepartmentBuilderWhenToStringThenReturnString() {
        Department.DepartmentBuilder departmentBuilder = Department.builder()
                .id(1L)
                .name("Valid_Name");

        String builderToString = "Department.DepartmentBuilder(id=1, name=Valid_Name)";
        Assertions.assertThat(departmentBuilder.toString()).isEqualTo(builderToString);
    }

    @Test
    public void givenDepartmentAndNullWhenEqualsThenReturnFalse() {
        Department department = Department.builder().id(1L).name("Valid_Name").build();
        Assertions.assertThat(department == null).isFalse();
    }

    @Test
    public void givenEqualDepartmentsAndDifferentIdTypeWhenEqualsThenReturnFalse() {
        Department department1 = Department.builder().id(1L).name("Valid_Name").build();
        Department department2 = Department.builder().id(2L).name("Valid_Name").build();
        Assertions.assertThat(department1.equals(department2)).isFalse();
    }

    @Test
    public void givenDifferentDepartmentsAndDifferentIdTypesWhenCanEqualThenReturnFalse() {
        Department department1 = Department.builder().id(1L).name("Valid_Name").build();
        Object differentTypeObject = new Object();
        Assertions.assertThat(department1.canEqual(differentTypeObject)).isFalse();
    }
}
