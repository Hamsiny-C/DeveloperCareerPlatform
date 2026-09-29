package com.devcareeros.controller;

import com.devcareeros.entity.Interview;
import com.devcareeros.security.UserPrincipal;
import com.devcareeros.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping
    public ResponseEntity<List<Interview>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(interviewService.getAllForUser(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<Interview> create(@RequestBody Interview interview,
                                             @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(interviewService.create(interview, principal.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interview> update(@PathVariable Long id, @RequestBody Interview interview,
                                             @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(interviewService.update(id, interview, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        interviewService.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
