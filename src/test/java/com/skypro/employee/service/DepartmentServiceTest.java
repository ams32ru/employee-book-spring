package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;
    private final List<Employee> employees = List.of(
            new Employee("TestA", "TestA", 1, 5000),
            new Employee("TestB", "TestB", 1, 6000),
            new Employee("TestC", "TestC", 2, 7000),
            new Employee("TestD", "TestD", 2, 8000),
            new Employee("TestI", "TestI", 3, 9000)
    );

    @BeforeEach
    void setUp() {
        when(employeeService.getAllEmployees()).thenReturn(employees);
    }

    @Test
    void getDepartmentEmployees() {
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        Assertions.assertThat(employeeList)
                .hasSize(2)
                .contains(employees.get(0),
                        employees.get(1));
    }

    @Test
    void getSummOfSallariesByDepartment() {
        int sum = this.departmentService.getSummOfSallariesInDepartment(1);
        Assertions.assertThat(sum).isEqualTo(11000);
    }

    @Test
    void getMaxSallaryByDepartment() {
        int maxSalary = this.departmentService.getMaxSallaryInDepartment(2);
        Assertions.assertThat(maxSalary).isEqualTo(8000);
    }

    @Test
    void getMinSallaryByDepartment() {
        int minSalary = this.departmentService.getMinSallaryInDepartment(2);
        Assertions.assertThat(minSalary).isEqualTo(7000);
    }

    @Test
    void getEmployeesGroupedByDepartments() {
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService.getEmployeesGroupedByDepartments();
        Assertions.assertThat(groupedEmployees).hasSize(3)
                .containsEntry(1,
                        List.of(employees.get(0), employees.get(1)))
                .containsEntry(2,
                        List.of(employees.get(2), employees.get(3)))
                .containsEntry(3,
                        List.of(employees.get(4)));
    }

    @Test
    void WhenNoEmployeesThenGroupByReternEmptyMap() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService.getEmployeesGroupedByDepartments();
        Assertions.assertThat(groupedEmployees).isEmpty();
    }

    @Test
    void WhenNoEmployessThenMaxSalaryInDepartmentTrowsException() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() ->
                        departmentService.getMinSallaryInDepartment(0))
                .isInstanceOf(FileNotFoundException.class);
    }
}