package com.github.springtest.service.impl;

import com.github.springtest.exception.ResourceNotFoundException;
import com.github.springtest.model.Department;
import com.github.springtest.model.Employee;
import com.github.springtest.repository.DepartmentRepository;
import com.github.springtest.repository.EmployeeRepository;
import com.github.springtest.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Department saveDepartment(Department department) {
        Optional<Department> savedDepartment = departmentRepository.findByName(department.getName());
        if (savedDepartment.isPresent()) {
            throw new ResourceNotFoundException(
                    "Department already exists with given name: " + department.getName()
            );
        }
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(Long id) {
        return employeeRepository.findEmployeesByDepartment(id);
    }

}