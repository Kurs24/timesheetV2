package com.yasykur.timesheet.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer roleId;
}
