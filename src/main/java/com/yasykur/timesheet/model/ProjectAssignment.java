package com.yasykur.timesheet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_tr_assigned_to")
public class ProjectAssignment {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
