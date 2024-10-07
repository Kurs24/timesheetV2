package com.yasykur.timesheet.controller.Rest;

import com.yasykur.timesheet.DTO.LoginDTO;
import com.yasykur.timesheet.DTO.RegisterDTO;
import com.yasykur.timesheet.DTO.SetPasswordDTO;
import com.yasykur.timesheet.config.JwtService;
import com.yasykur.timesheet.config.MyUserDetails;
import com.yasykur.timesheet.exception.CustomException;
import com.yasykur.timesheet.exception.UnauthorizedException;
import com.yasykur.timesheet.handler.CustomResponse;
import com.yasykur.timesheet.handler.ResponseUtil;
import com.yasykur.timesheet.model.ApiResponse;
import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.model.Pin;
import com.yasykur.timesheet.model.Role;
import com.yasykur.timesheet.repository.CredentialRepository;
import com.yasykur.timesheet.service.*;
import com.yasykur.timesheet.util.EmployeeStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("api/v2/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO loginData, HttpServletRequest request) {

        String token = authenticationService.login(loginData);

        ApiResponse<String> response =
                ResponseUtil.success(token, "Login Success",
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("register")
    @Transactional
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterDTO registerDTO,
                                                      HttpServletRequest request) {
        authenticationService.register(registerDTO);
        ApiResponse<Void> response =
                ResponseUtil.success(null, "Create new Employee Success", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("verify-pin/")
    public ResponseEntity<Object> verifyPin(@RequestParam("pin") String pin, HttpServletRequest request) {
        authenticationService.verifyPin(pin);
        ApiResponse<Void> response = ResponseUtil.success(null, "Pin Verified", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("set-password")
    @Transactional
    public ResponseEntity<Object> setPassword(@RequestBody SetPasswordDTO data, @RequestParam("pin") String pin,
                                              HttpServletRequest request) {
        authenticationService.setPassword(data, pin);
        ApiResponse<Void> response = ResponseUtil.success(null, "Your password has been set!", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
