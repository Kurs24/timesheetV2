package com.yasykur.timesheet.model;

import com.yasykur.timesheet.util.TimeSheetStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "tb_tr_daily_timesheet")
public class DailyTimesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(columnDefinition = "TEXT")
    private String activity;

    @ManyToOne
    @JoinColumn(name = "project_timesheet__id")
    private ProjectTimesheet projectTimesheet;
}
