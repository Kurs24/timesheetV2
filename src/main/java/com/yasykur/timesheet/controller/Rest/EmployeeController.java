package com.yasykur.timesheet.controller.Rest;

import com.yasykur.timesheet.DTO.EmployeeDTO;
import com.yasykur.timesheet.handler.CustomResponse;
import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.service.EmployeeServiceImpl;
import com.yasykur.timesheet.util.EmployeeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @GetMapping("employee")
    public ResponseEntity<Object> getEmployeeList() {
        try {
            return CustomResponse.generate(HttpStatus.OK, "Successfully Fetch Employee Data",
                    employeeService.getEmployeeList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("employee")
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeDTO data) {
        try {
            Employee newEmployee = Employee.builder()
                    .firstName(data.getFirstName())
                    .lastName(data.getLastName())
                    .email(data.getEmail())
                    .phoneNumber(data.getPhoneNumber())
                    .status(EmployeeStatus.ACTIVE)
                    .build();
            return CustomResponse.generate(HttpStatus.CREATED, "Successfully Create New Employee",
                    employeeService.createEmployee(newEmployee));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") Integer id) {
        try {
            return CustomResponse.generate(HttpStatus.OK, "Delete Employee Success",
                    employeeService.deleteEmployee(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
