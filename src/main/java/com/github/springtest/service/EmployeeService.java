package com.github.springtest.service;

import com.github.springtest.model.Department;
import com.github.springtest.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee (Employee employee);

    List<Employee> getAllEmployees ();

    Optional<Employee> getEmployeeById (Long id);

    Employee updateEmployee (Employee employee);

    void deleteEmployee (Long id);

    List<Employee> getEmployeesByDepartment(String name);
}
