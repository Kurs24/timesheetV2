package com.yasykur.timesheet.controller.Rest;

import com.yasykur.timesheet.DTO.LoginDTO;
import com.yasykur.timesheet.DTO.RegisterDTO;
import com.yasykur.timesheet.DTO.SetPasswordDTO;
import com.yasykur.timesheet.config.JwtSService;
import com.yasykur.timesheet.config.MyUserDetails;
import com.yasykur.timesheet.handler.CustomResponse;
import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.model.Pin;
import com.yasykur.timesheet.model.Role;
import com.yasykur.timesheet.service.*;
import com.yasykur.timesheet.util.EmployeeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final CredentialService credentialService;
    private final MyUserDetails myUserDetails;
    private final JwtSService jwtSService;

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginData) {
        Employee employee = employeeService.getEmployeeByEmail(loginData.getEmail());

        if (employee == null || employee.getStatus() == EmployeeStatus.NOT_ACTIVE) {
            return CustomResponse.generate(HttpStatus.UNAUTHORIZED, "Email or Password Wrong");
        }

        boolean isValid = passwordEncoder.matches(loginData.getPassword(), employee.getCredential().getPassword());

        if (!isValid) {
            return CustomResponse.generate(HttpStatus.UNAUTHORIZED, "Email or Password Wrong");
        }

        UserDetails userDetails = myUserDetails.loadUserByUsername(employee.getEmail());

        return CustomResponse.generate(HttpStatus.OK, "Login Success",
                jwtSService.generateToken(userDetails, employee));
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

    @PostMapping("set-password")
    @Transactional
    public ResponseEntity<Object> setPassword(@RequestBody SetPasswordDTO data, @RequestParam("pin") String pin) {
        if (data.getOldPassword() != null) {
            return CustomResponse.generate(HttpStatus.OK, "testing");
        }

        boolean isPinVerified = pinService.verifyPin(pin);

        if (!isPinVerified) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Pin Invalid, please Request another Pin");
        }

        Pin foundPin = pinService.getPinByPin(pin);

        credentialService.createCredential(foundPin.getId(), passwordEncoder.encode(data.getPassword()));

        pinService.deletePin(pin);
        return CustomResponse.generate(HttpStatus.OK, "Your password has been set!");
    }
}
