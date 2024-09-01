package com.yasykur.timesheet.service;

import com.yasykur.timesheet.DTO.CreateProjectDTO;
import com.yasykur.timesheet.model.Project;

public interface ProjectService {
    public Project createProject(CreateProjectDTO data);
}
