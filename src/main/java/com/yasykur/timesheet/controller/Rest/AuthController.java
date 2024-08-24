package com.yasykur.timesheet.controller.Rest;

import com.yasykur.timesheet.DTO.LoginDTO;
import com.yasykur.timesheet.DTO.RegisterDTO;
import com.yasykur.timesheet.handler.CustomResponse;
import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.model.Pin;
import com.yasykur.timesheet.model.Role;
import com.yasykur.timesheet.service.*;
import com.yasykur.timesheet.util.EmployeeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/auth")
@RequiredArgsConstructor
public class AuthController {

    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final RoleService roleService;
    private final PinService pinService;

    @PostMapping("login")
    public ResponseEntity<Object> login(LoginDTO loginData) {
        Employee employee = employeeService.getEmployeeByEmail(loginData.getEmail());

        if (employee == null) {
            return CustomResponse.generate(HttpStatus.UNAUTHORIZED, "Email or Password Wrong");
        }

        return CustomResponse.generate(HttpStatus.OK, "Login Success");
    }


    @PostMapping("register")
    @Transactional
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

            Pin createdPin = pinService.createPin(idCreated);

            if (idCreated != 0) {
                // add link for FE verify
                emailService.sendMail("Set Password for your Account", registerDTO.getEmail(), registerDTO.getEmail(),
                        "Create Account Success, Please Set Your Password using this pin " + createdPin.getPin());
                return CustomResponse.generate(HttpStatus.CREATED, "Successfully Create New Employee");
            }

            return CustomResponse.generate(HttpStatus.INTERNAL_SERVER_ERROR, "Create new Employee Failed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("verify-pin/")
    public ResponseEntity<Object> verifyPin(@RequestParam("pin") String pin) {
        try {
            boolean isPinVerified = pinService.verifyPin(pin);

            if (!isPinVerified) {
                return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Pin Invalid, please Request another Pin");
            }

            return CustomResponse.generate(HttpStatus.OK, "Pin Verified");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
