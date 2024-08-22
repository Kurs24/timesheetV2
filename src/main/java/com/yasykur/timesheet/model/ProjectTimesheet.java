package com.yasykur.timesheet.model;

import com.yasykur.timesheet.util.SubmissionTime;
import com.yasykur.timesheet.util.SubmissionType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_m_project_timesheet")
@Data
public class ProjectTimesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "submission_type")
    @Enumerated(EnumType.STRING)
    private SubmissionType submissionType;

    @Column(name = "submission_time")
    @Enumerated(EnumType.STRING)
    private SubmissionTime submissionTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
