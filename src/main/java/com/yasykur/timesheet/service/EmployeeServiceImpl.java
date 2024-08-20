package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

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
