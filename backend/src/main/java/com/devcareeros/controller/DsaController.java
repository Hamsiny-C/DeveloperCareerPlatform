package com.devcareeros.controller;

import com.devcareeros.entity.DsaProblem;
import com.devcareeros.security.UserPrincipal;
import com.devcareeros.service.DsaProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dsa")
public class DsaController {

    @Autowired
    private DsaProblemService dsaProblemService;

    @GetMapping
    public ResponseEntity<List<DsaProblem>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(dsaProblemService.getAllForUser(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<DsaProblem> create(@RequestBody DsaProblem problem,
                                              @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(dsaProblemService.create(problem, principal.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DsaProblem> update(@PathVariable Long id, @RequestBody DsaProblem problem,
                                              @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(dsaProblemService.update(id, problem, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        dsaProblemService.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
