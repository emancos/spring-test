package com.github.springtest.service.impl;

import com.github.springtest.exception.ResourceNotFoundException;
import com.github.springtest.model.Department;
import com.github.springtest.model.Employee;
import com.github.springtest.repository.DepartmentRepository;
import com.github.springtest.repository.EmployeeRepository;
import com.github.springtest.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Employee saveEmployee (Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) throw new ResourceNotFoundException(
                "Employee already exists with given email: " + employee.getEmail()
        );
        Employee employeeToSave = departmentRepository.findByName(employee.getDepartment().getName())
                .map(department -> {
                    employee.setDepartment(department);
                    return employee;
                }).orElseThrow();
        return employeeRepository.save(employeeToSave);
    }

    @Override
    public List<Employee> getAllEmployees () {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById (Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee (Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String name) {
        Optional<Department> department = departmentRepository.findByName(name);
        return department.map(
                value -> employeeRepository.findEmployeesByDepartment(value.getId())
        ).orElse(null);
    }
}
