package com.yasykur.timesheet.model;

import com.yasykur.timesheet.util.EmployeeStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_m_employee")
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
}
