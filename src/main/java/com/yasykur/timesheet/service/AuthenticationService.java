package com.yasykur.timesheet.service;

import com.yasykur.timesheet.DTO.LoginDTO;
import com.yasykur.timesheet.DTO.RegisterDTO;
import com.yasykur.timesheet.DTO.SetPasswordDTO;

public interface AuthenticationService {
    String login(LoginDTO data);
    void register(RegisterDTO data);
    void verifyPin(String pin);
    void setPassword(SetPasswordDTO data, String pin);
}
