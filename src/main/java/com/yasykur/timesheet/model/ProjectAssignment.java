package com.yasykur.timesheet.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_tr_assigned_to")
@Data
public class ProjectAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
