package com.devcareeros.controller;

import com.devcareeros.entity.JobApplication;
import com.devcareeros.security.UserPrincipal;
import com.devcareeros.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(jobApplicationService.getAllForUser(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<JobApplication> create(@RequestBody JobApplication application,
                                                  @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(jobApplicationService.create(application, principal.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> update(@PathVariable Long id, @RequestBody JobApplication application,
                                                  @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(jobApplicationService.update(id, application, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        jobApplicationService.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
