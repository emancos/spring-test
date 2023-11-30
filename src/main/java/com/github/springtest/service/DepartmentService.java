package com.github.springtest.service;

import com.github.springtest.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Department saveDepartment(Department department);

    List<Department> getAllDepartments();

    Optional<Department> getDepartmentById(Long id);

    Department updateDepartment(Department department);

    void deleteDepartment(Long id);
}
