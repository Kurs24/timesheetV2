package com.yasykur.timesheet.DTO;

import lombok.Data;

@Data
public class SetPasswordDTO {
    private String oldPassword;
    private String password;
}
