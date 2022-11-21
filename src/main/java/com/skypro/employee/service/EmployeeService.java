package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException(" Не введено имя или фамилия");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartament(),
                employeeRequest.getSalary());
        this.employees.put(employee.getId(),employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getSalaryMin() {
        int idMin = 0;
        int max = Integer.MAX_VALUE;
        for (Employee value : employees.values()) {
            if (value.getSalary() < max) {
                max= value.getSalary();
               idMin = value.getId();
            }
        }
        return employees.get(idMin);
    }

    public Employee getSalaryMax() {
        int idMax = 0;
        int min = Integer.MIN_VALUE;
        for (Employee value : employees.values()) {
            if (value.getSalary() > min) {
                min=value.getSalary();
                idMax = value.getId();
            }
        }
        return employees.get(idMax);
    }

    public List getHighSalary() {
//        List <Employee> highEmployee = new ArrayList<>();
       int average = getSalarySum()/employees.size();
//        for (Employee value : employees.values()) {
//            if (value.getSalary() > average) {
//                highEmployee.add(value);
//            }
//        }
//        return highEmployee;
        return employees.values().stream().filter(s -> s.getSalary() > average).collect(Collectors.toList());
   }

    public Employee removeEmployee(int id) {
        return employees.remove(id);
    }

}
