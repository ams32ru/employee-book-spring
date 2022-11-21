package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("TestA", "TestA", 1, 5000),
                new EmployeeRequest("TestB", "TestB", 1, 6000),
                new EmployeeRequest("TestC", "TestC", 2, 7000),
                new EmployeeRequest("TestD", "TestD", 2, 8000),
                new EmployeeRequest("TestI", "TestI", 3, 9000)
        ).forEach(employeeService::addEmployee);}

    @Test
    void addEmployeeTest() {
            EmployeeRequest request = new EmployeeRequest
                    ("Valid", "Valid", 1, 1000);
            Employee result = employeeService.addEmployee(request);
            assertEquals(result.getFirstName(),request.getFirstName());
            assertEquals(result.getLastName(),request.getLastName());
            assertEquals(result.getDepartament(),request.getDepartament());
            assertEquals(result.getSalary(),request.getSalary());

        Assertions.assertThat(employeeService.getAllEmployees().contains(result));
        }

    void listEmployeeTest() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5);
    }

    @Test
    void summOfSallary() {
        int sum = employeeService.getSalarySum();
        Assertions.assertThat(sum).isEqualTo(35000);
    }

    @Test
    void employeeWithMaxSalary() {
        Employee employee = employeeService.getSalaryMax();
        Assertions.assertThat(employee).isNotNull()
                .extracting(Employee::getFirstName).
                isEqualTo("TestI");
    }

    @Test
    void employeeWithMinSalary() {
        Employee employee = employeeService.getSalaryMin();
        Assertions.assertThat(employee).isNotNull()
                .extracting(Employee::getFirstName).
                isEqualTo("TestA");
    }

    @Test
    void employeeHigsSalary() {
        List highSalary = employeeService.getHighSalary();
        Assertions.assertThat(highSalary).hasSize(2);
    }

    @Test
    void removeEmploye() {
        employeeService.removeEmployee(1);
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(4);
    }
}