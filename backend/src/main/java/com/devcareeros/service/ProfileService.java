package com.devcareeros.service;

import com.devcareeros.dto.ProfileUpdateRequest;
import com.devcareeros.entity.User;
import com.devcareeros.exception.ResourceNotFoundException;
import com.devcareeros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public User getProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User updateProfile(Long userId, ProfileUpdateRequest request) {
        User user = getProfile(userId);
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        user.setJobTitle(request.getJobTitle());
        user.setTargetRole(request.getTargetRole());
        user.setBio(request.getBio());
        return userRepository.save(user);
    }
}
