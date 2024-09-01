package com.yasykur.timesheet.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateProjectDTO {
    @NotNull
    private String name;

    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;

    @NotNull
    private Integer leaderId;
}
