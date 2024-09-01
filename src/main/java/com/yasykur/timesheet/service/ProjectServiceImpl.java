package com.yasykur.timesheet.service;

import com.yasykur.timesheet.DTO.CreateProjectDTO;
import com.yasykur.timesheet.model.Employee;
import com.yasykur.timesheet.model.Project;
import com.yasykur.timesheet.repository.EmployeeRepository;
import com.yasykur.timesheet.repository.ProjectRepository;
import com.yasykur.timesheet.util.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(CreateProjectDTO data) {
        Employee projectLeader =
                employeeRepository.findById(data.getLeaderId())
                        .orElseThrow(() -> new UsernameNotFoundException("Employee Not Found"));

        if (!projectLeader.getRole().getName().equals("Project Manager")) {
            throw new RuntimeException("Only Project Manager Can be a Project Leader");
        }

        return projectRepository.save(
                Project.builder()
                        .name(data.getName())
                        .status(ProjectStatus.ON_PROGRESS)
                        .startDate(data.getStartDate())
                        .endDate(data.getEndDate())
                        .projectLeader(projectLeader)
                        .build());
    }
}