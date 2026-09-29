package com.devcareeros.controller;

import com.devcareeros.entity.LearningTopic;
import com.devcareeros.security.UserPrincipal;
import com.devcareeros.service.LearningTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
public class LearningController {

    @Autowired
    private LearningTopicService learningTopicService;

    @GetMapping
    public ResponseEntity<List<LearningTopic>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(learningTopicService.getAllForUser(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<LearningTopic> create(@RequestBody LearningTopic topic,
                                                 @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(learningTopicService.create(topic, principal.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningTopic> update(@PathVariable Long id, @RequestBody LearningTopic topic,
                                                 @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(learningTopicService.update(id, topic, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        learningTopicService.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
