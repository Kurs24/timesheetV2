package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployeeList() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Integer createEmployee(Employee data) {
        if (data != null) {
            employeeRepository.save(data);
            return data.getId();
        }
        return 0;
    }

    @Override
    public Boolean deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);

        return employeeRepository.findById(id).isEmpty();
    }

    @Override
    public Boolean editEmployee(Employee data) {
        if (data != null) {
            employeeRepository.save(data);
            return true;
        }
        return false;
    }
}
