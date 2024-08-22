package com.yasykur.timesheet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yasykur.timesheet.util.ProjectStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tb_m_project")
@Data
public class Project {
    @Id
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "project_leader")
    private Employee projectLeader;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<ProjectAssignment> assignedMember;
}
