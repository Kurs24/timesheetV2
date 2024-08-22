package com.yasykur.timesheet.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_m_credential")
@Data
public class Credential {
    @Id
    private Integer id;

    private String password;

    @OneToOne
    @JoinColumn(name = "id")
    private Employee employee;
}
