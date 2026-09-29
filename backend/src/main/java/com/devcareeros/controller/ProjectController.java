package com.devcareeros.controller;

import com.devcareeros.entity.Project;
import com.devcareeros.security.UserPrincipal;
import com.devcareeros.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(projectService.getAllForUser(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project,
                                           @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(projectService.create(project, principal.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody Project project,
                                           @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(projectService.update(id, project, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        projectService.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
