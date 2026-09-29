package com.devcareeros.controller;

import com.devcareeros.dto.ProfileUpdateRequest;
import com.devcareeros.entity.User;
import com.devcareeros.security.UserPrincipal;
import com.devcareeros.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(profileService.getProfile(principal.getId()));
    }

    @PutMapping
    public ResponseEntity<User> updateProfile(@RequestBody ProfileUpdateRequest request,
                                               @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(profileService.updateProfile(principal.getId(), request));
    }
}
