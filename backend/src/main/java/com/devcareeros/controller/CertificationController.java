package com.devcareeros.controller;

import com.devcareeros.entity.Certification;
import com.devcareeros.security.UserPrincipal;
import com.devcareeros.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    @GetMapping
    public ResponseEntity<List<Certification>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(certificationService.getAllForUser(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<Certification> create(@RequestBody Certification certification,
                                                 @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(certificationService.create(certification, principal.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certification> update(@PathVariable Long id, @RequestBody Certification certification,
                                                 @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(certificationService.update(id, certification, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        certificationService.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
