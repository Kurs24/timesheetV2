package com.yasykur.timesheet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_m_day_off")
@Data
public class DayOff {
    @Id
    private String dayOffDate;

    private String description;
}
