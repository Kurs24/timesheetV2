package com.yasykur.timesheet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yasykur.timesheet.util.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "tb_m_employee")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private Credential credential;

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private Pin pin;

    @OneToMany(mappedBy = "projectLeader")
    @JsonIgnore
    private List<Project> projectHandledAsLead;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<ProjectAssignment> projectAsMember;
}
