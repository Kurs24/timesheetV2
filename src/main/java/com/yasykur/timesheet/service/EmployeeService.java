package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeeList();

    Employee getEmployeeByEmail(String email);

    Integer createEmployee(Employee employee);

    Boolean deleteEmployee(Integer id);

    Boolean editEmployee(Employee employee);
}
