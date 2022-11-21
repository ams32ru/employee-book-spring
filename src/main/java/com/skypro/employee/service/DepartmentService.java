package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Stream<Employee> getEmployeesByDepartmentStream(int department) {
        return employeeService.getAllEmployees().stream()
                .filter(e -> e.getDepartament() == department);
    }

    public Collection<Employee> getDepartmentEmployees(int department) {
        return getEmployeesByDepartmentStream(department)
                .collect(Collectors.toList());
    }

    public int getSummOfSallariesInDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(e -> e.getSalary()).sum();
    }

    public int getMaxSallaryInDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(e -> e.getSalary()).max()
                .orElseThrow(RuntimeException::new);
    }

    public int getMinSallaryInDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(e -> e.getSalary()).min()
                .orElseThrow(RuntimeException::new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartments() {
        return employeeService.getAllEmployees().stream()
                .collect(Collectors.groupingBy(e -> e.getDepartament()));
    }


}
