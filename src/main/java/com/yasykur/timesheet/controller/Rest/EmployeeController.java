package com.yasykur.timesheet.controller.Rest;

import com.yasykur.timesheet.DTO.EmployeeDTO;
import com.yasykur.timesheet.handler.ResponseUtil;
import com.yasykur.timesheet.model.ApiResponse;
import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.service.EmployeeServiceImpl;
import com.yasykur.timesheet.util.EmployeeStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @GetMapping("employee")
    public ResponseEntity<ApiResponse<List<Employee>>> getEmployeeList(HttpServletRequest request) {
        try {
            List<Employee> employees = employeeService.getEmployeeList();

            ApiResponse<List<Employee>> response =
                    ResponseUtil.success(employees, "Successfully Fetch Employee Data", request.getRequestURI());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("employee")
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody EmployeeDTO data,
                                                                HttpServletRequest request) {
        try {
            Employee newEmployee = Employee.builder()
                    .firstName(data.getFirstName())
                    .lastName(data.getLastName())
                    .email(data.getEmail())
                    .phoneNumber(data.getPhoneNumber())
                    .status(EmployeeStatus.ACTIVE)
                    .build();

            ApiResponse<Employee> response =
                    ResponseUtil.success(newEmployee, "Successfully Create New Employee", request.getRequestURI());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteEmployee(@PathVariable("id") Integer id,
                                                               HttpServletRequest request) {
        try {

            Boolean isDeleted = employeeService.deleteEmployee(id);

            ApiResponse<Boolean> response =
                    ResponseUtil.success(isDeleted, "Successfully Delete Employee", request.getRequestURI());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
