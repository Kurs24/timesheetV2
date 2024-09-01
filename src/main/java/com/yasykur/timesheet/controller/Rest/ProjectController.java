package com.yasykur.timesheet.controller.Rest;

import com.yasykur.timesheet.DTO.CreateProjectDTO;
import com.yasykur.timesheet.handler.CustomResponse;
import com.yasykur.timesheet.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("project")
    public ResponseEntity<Object> createProject(@RequestBody CreateProjectDTO data) {
        try {
            return CustomResponse.generate(HttpStatus.CREATED, "Successfully Create New Project",
                    projectService.createProject(data));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
