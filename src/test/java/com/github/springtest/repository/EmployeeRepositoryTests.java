package com.github.springtest.repository;

import com.github.springtest.model.Department;
import com.github.springtest.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    public static final String VALID_NAME_EMPLOYEE = "Valid_Name_Employee";
    public static final String VALID_LAST_NAME_EMPLOYEE = "Valid_Last_Name_Employee";
    public static final String VALID_EMAIL_EMPLOYEE = "valid_email_employee@mail.com";
    private Employee employee;
    private Department department;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setup() {
        department = departmentRepository.save(new Department(1L, "IT"));
        employee = Employee.builder()
                .firstName(VALID_NAME_EMPLOYEE)
                .lastName(VALID_LAST_NAME_EMPLOYEE)
                .email(VALID_EMAIL_EMPLOYEE)
                .department(department)
                .build();
    }

    @DisplayName("JUnit test for save employee operation")
    @Test
    void givenEmployeeObjectWhenSaveEmployeeThenReturnSavedEmployee() {
        Employee employeeSaved = employeeRepository.save(employee);
        assertThat(employeeSaved).isNotNull();
        assertThat(employeeSaved.getId()).isPositive();
    }

    @DisplayName("JUnit test for get all employees operation")
    @Test
    void givenEmployeeListWhenFindAllThenEmployeeList() {
        var employees = getListEmployee(5, department);
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(employees.size()).isNotNull();
    }

    @DisplayName("JUnit test for get all pageable employees operation")
    @Test
    void givenEmployeeListPageableWhenfindAllPageableThenEmployeeListPageable() {
        PageRequest pageable = PageRequest.of(0,10);
        getListEmployee(20, department);
        Page<Employee> employeeListPageable = employeeRepository.findAllPageable(pageable);
        assertThat(employeeListPageable).hasSize(pageable.getPageSize()).isNotNull();
        assertThat(employeeListPageable.getSize()).isEqualTo(10);
    }

    @DisplayName("JUnit test for get employee by id operation")
    @Test
    void givenEmplyeeObjectWhenFindByIdThenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeById = employeeRepository.findById(employee.getId()).orElse(null);
        assertThat(employeeById).isNotNull();
    }

    @DisplayName("JUnit test for get employee by email operation")
    @Test
    void givenEmplyeeObjectWhenFindByEmailThenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByEmail = employeeRepository.findByEmail(employee.getEmail()).orElse(null);
        assertThat(employeeByEmail).isNotNull();
    }

    @DisplayName("JUnit test for update employee operation")
    @Test
    void givenEmployeeObjectWhenUpdateEmployeeThenReturnUpdatedEmployee() {
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        assertThat(savedEmployee).isNotNull();
        savedEmployee.setFirstName("Valid_Name_Employee_Updated");
        savedEmployee.setLastName("Valid_Last_Name_Employee_Updated");
        savedEmployee.setEmail("valid_email_employee_updated@mail.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Valid_Name_Employee_Updated");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Valid_Last_Name_Employee_Updated");
        assertThat(updatedEmployee.getEmail()).isEqualTo("valid_email_employee_updated@mail.com");

    }

    @DisplayName("JUnit test for delete employee operation")
    @Test
    void givenEmplyeeObjectWhenDeleteEmployeeThenRemoveEmployee() {
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        assertThat(deletedEmployee).isEmpty();
    }

    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    void givenFirstNameAndLastNameWhenFindByJPQLThenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository
                .findByJPQL(VALID_NAME_EMPLOYEE, VALID_LAST_NAME_EMPLOYEE);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for custom query using JPQL with named params")
    @Test
    void givenFirstNameAndLastNameWhenFindByJPQLNamedParamsThenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository
                .findByJPQLNamedParams(VALID_NAME_EMPLOYEE, VALID_LAST_NAME_EMPLOYEE);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for native query using native SQL with index")
    @Test
    void givenFirstNameAndLastNameWhenFindByNativeSQLThenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository
                .findByNativeSQL(VALID_NAME_EMPLOYEE, VALID_LAST_NAME_EMPLOYEE);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for native query using native SQL with named params")
    @Test
    void givenFirstNameAndLastNameWhenFindByNativeSQLNamedParamsThenReturnEmployeeObject() {
        employeeRepository.save(employee);
        Employee employeeByJPQL = employeeRepository
                .findByNativeSQLNamedParams(VALID_NAME_EMPLOYEE, VALID_LAST_NAME_EMPLOYEE);
        assertThat(employeeByJPQL).isNotNull();
    }

    @DisplayName("JUnit test for get employees by department operation")
    @Test
    void givenDepartmentIdWhenFindEmployeesByDepartmentThenReturnEmployeeList() {
        int numberOfEmployees = 3;
        List<Employee> employees = getListEmployee(numberOfEmployees, department);

        List<Employee> employeesByDepartment = employeeRepository.findEmployeesByDepartment(department.getId());
        assertThat(employeesByDepartment).hasSize(numberOfEmployees);
    }

    @DisplayName("JUnit test for get employees by department name operation")
    @Test
    void givenDepartmentNameWhenFindEmployeesByDepartmentNameThenReturnEmployeeList() {
        int numberOfEmployees = 3;
        List<Employee> employees = getListEmployee(numberOfEmployees, department);
        List<Employee> employeesByDepartmentName = employeeRepository.findEmployeesByDepartment(department.getId());
        assertThat(employeesByDepartmentName).hasSize(employees.size());
    }

    private List<Employee> getListEmployee(int listSize, Department department) {
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            Employee aEmployee = Employee.builder()
                    .firstName(VALID_NAME_EMPLOYEE + i)
                    .lastName(VALID_LAST_NAME_EMPLOYEE + i)
                    .email("valid_email_employee" + i + "@mail.com")
                    .department(department)
                    .build();
            employeeRepository.save(aEmployee);
            employeeList.add(aEmployee);
        }
        return employeeList;
    }
}
