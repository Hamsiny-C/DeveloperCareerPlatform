package com.devcareeros.controller;

import com.devcareeros.entity.Skill;
import com.devcareeros.security.UserPrincipal;
import com.devcareeros.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Every method below reads the currently logged-in user from
 * @AuthenticationPrincipal UserPrincipal - this is filled in automatically
 * by our JwtAuthFilter once it has validated the token. This is how we
 * make sure a user only ever sees / edits THEIR OWN data.
 */
@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<List<Skill>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(skillService.getAllForUser(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<Skill> create(@RequestBody Skill skill, @AuthenticationPrincipal UserPrincipal principal) {
        Skill created = skillService.create(skill, principal.getUser());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> update(@PathVariable Long id, @RequestBody Skill skill,
                                         @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(skillService.update(id, skill, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        skillService.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
