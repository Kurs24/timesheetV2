package com.yasykur.timesheet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_m_pin")
@Getter
@Setter
@NoArgsConstructor
public class Pin {
    @Id
    private Integer id;

    private String pin;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @OneToOne
    @JoinColumn(name = "id")
    private Employee employee;

    public Pin(Integer id, String pin) {
        this.id = id;
        this.pin = pin;
        expireDate = LocalDateTime.now().plusHours(12);
    }
}
