package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    Integer createEmployee(Employee employee);
    Boolean deleteEmployee(Integer id);
    Boolean editEmployee(Employee employee);
}
