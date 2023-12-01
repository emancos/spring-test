package com.github.springtest.controller;

import com.github.springtest.model.Department;
import com.github.springtest.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        var savedDepartment = service.saveDepartment(department);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDepartment.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedDepartment);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = service.getAllDepartments();
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {
        return service.getDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return service.getDepartmentById(id).map(savedDepartment -> {
            savedDepartment.setName(department.getName());
            Department updatedDepartment = service.updateDepartment(savedDepartment);
            return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") Long id) {
        service.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
