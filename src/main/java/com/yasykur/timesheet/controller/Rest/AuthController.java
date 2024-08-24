package com.yasykur.timesheet.controller.Rest;

import com.yasykur.timesheet.DTO.LoginDTO;
import com.yasykur.timesheet.DTO.RegisterDTO;
import com.yasykur.timesheet.handler.CustomResponse;
import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.model.Role;
import com.yasykur.timesheet.service.*;
import com.yasykur.timesheet.util.EmployeeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/auth")
@RequiredArgsConstructor
public class AuthController {

    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final RoleService roleService;

    @PostMapping("login")
    public ResponseEntity<Object> login(LoginDTO loginData) {
        Employee employee = employeeService.getEmployeeByEmail(loginData.getEmail());

        if (employee == null) {
            return CustomResponse.generate(HttpStatus.UNAUTHORIZED, "Email or Password Wrong");
        }

        return CustomResponse.generate(HttpStatus.OK, "Login Success");
    }


    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO) {
        try {
            Employee foundEmployee = employeeService.getEmployeeByEmail(registerDTO.getEmail());

            if (foundEmployee != null) {
                return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Email Already used");
            }

            Role foundRole = roleService.getRoleById(registerDTO.getRoleId());

            if (foundRole == null) {
                return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Role Not Found");
            }

            Employee newEmployee = Employee.builder()
                    .firstName(registerDTO.getFirstName())
                    .lastName(registerDTO.getLastName())
                    .email(registerDTO.getEmail())
                    .phoneNumber(registerDTO.getPhoneNumber())
                    .status(EmployeeStatus.ACTIVE)
                    .role(foundRole)
                    .build();

            Integer idCreated = employeeService.createEmployee(newEmployee);

            if (idCreated != 0) {
                // add link for FE verify
                emailService.sendMail("Set Password for your Account", registerDTO.getEmail(), registerDTO.getEmail(), "Create Account Success, Please Set Your Password");
                return CustomResponse.generate(HttpStatus.CREATED, "Successfully Create New Employee");
            }

            return CustomResponse.generate(HttpStatus.INTERNAL_SERVER_ERROR, "Create new Employee Failed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
