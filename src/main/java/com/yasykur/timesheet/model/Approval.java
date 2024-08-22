package com.yasykur.timesheet.model;

import com.yasykur.timesheet.util.ApprovalStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_m_approval")
@Data
public class Approval {
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "project_leader_id")
    private Employee projectLeader;

    @ManyToOne
    @JoinColumn(name = "human_resource_id")
    private Employee humanResource;

}
