package com.yasykur.timesheet.service;

import com.yasykur.timesheet.DTO.LoginDTO;
import com.yasykur.timesheet.DTO.RegisterDTO;
import com.yasykur.timesheet.DTO.SetPasswordDTO;
import com.yasykur.timesheet.config.JwtService;
import com.yasykur.timesheet.config.MyUserDetails;
import com.yasykur.timesheet.exception.CustomException;
import com.yasykur.timesheet.exception.UnauthorizedException;
import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.model.Pin;
import com.yasykur.timesheet.model.Role;
import com.yasykur.timesheet.util.EmployeeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetails myUserDetails;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final PinService pinService;
    private final EmailService emailService;
    private final CredentialService credentialService;

    @Override
    public String login(LoginDTO data) {
        Employee employee = employeeService.getEmployeeByEmail(data.getEmail());

        if (employee == null || employee.getStatus() == EmployeeStatus.NOT_ACTIVE) {
            throw new UnauthorizedException("Email or Password Wrong");
        }

        boolean isValid = passwordEncoder.matches(data.getPassword(), employee.getCredential().getPassword());

        if (!isValid) {
            throw new UnauthorizedException("Email or Password Wrong");
        }

        UserDetails userDetails = myUserDetails.loadUserByUsername(employee.getEmail());

        if (userDetails == null || !userDetails.isEnabled() || !userDetails.isAccountNonLocked() ||
                !userDetails.isAccountNonExpired() || !userDetails.isCredentialsNonExpired()) {
            throw new UnauthorizedException("Email or Password Wrong");
        }

        return jwtService.generateToken(userDetails, employee);
    }

    @Override
    @Transactional
    public void register(RegisterDTO data) {
        Employee foundEmployee = employeeService.getEmployeeByEmail(data.getEmail());
        if (foundEmployee != null) {
            throw new CustomException("Email Already used", HttpStatus.CONFLICT);
        }

        Role foundRole = roleService.getRoleById(data.getRoleId());
        if (foundRole == null) {
            throw new CustomException("Role Not Found", HttpStatus.BAD_REQUEST);
        }

        Employee newEmployee = Employee.builder()
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .email(data.getEmail())
                .phoneNumber(data.getPhoneNumber())
                .status(EmployeeStatus.ACTIVE)
                .role(foundRole)
                .build();

        Integer idCreated = employeeService.createEmployee(newEmployee);
        if (idCreated == 0) {
            throw new CustomException("Failed Create Employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Pin createdPin = pinService.createPin(idCreated);

        // add link for FE verify
        emailService.sendMail("Set Password for your Account", data.getEmail(), data.getEmail(),
                "Create Account Success, Please Set Your Password using this pin " + createdPin.getPin());
    }

    @Override
    public void verifyPin(String pin) {
        boolean isPinVerified = pinService.verifyPin(pin);

        if (!isPinVerified) {
            throw new UnauthorizedException("Pin Invalid, please Request another Pin");
        }
    }

    @Override
    @Transactional
    public void setPassword(SetPasswordDTO data, String pin) {
        boolean isPinVerified = pinService.verifyPin(pin);

        if (!isPinVerified) {
            throw new UnauthorizedException("Pin Invalid, please Request another Pin");
        }

        Pin foundPin = pinService.getPinByPin(pin);

        credentialService.createCredential(foundPin.getId(), passwordEncoder.encode(data.getPassword()));

        pinService.deletePin(pin);
    }
}
