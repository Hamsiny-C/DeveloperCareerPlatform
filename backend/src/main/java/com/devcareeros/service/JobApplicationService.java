package com.devcareeros.service;

import com.devcareeros.entity.JobApplication;
import com.devcareeros.entity.User;
import com.devcareeros.exception.ResourceNotFoundException;
import com.devcareeros.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> getAllForUser(Long userId) {
        return jobApplicationRepository.findByUserId(userId);
    }

    public JobApplication create(JobApplication application, User user) {
        application.setUser(user);
        return jobApplicationRepository.save(application);
    }

    public JobApplication update(Long id, JobApplication updated, Long userId) {
        JobApplication existing = getOwnedOrThrow(id, userId);
        existing.setCompany(updated.getCompany());
        existing.setRole(updated.getRole());
        existing.setLocation(updated.getLocation());
        existing.setApplicationDate(updated.getApplicationDate());
        existing.setStatus(updated.getStatus());
        existing.setJobUrl(updated.getJobUrl());
        existing.setNotes(updated.getNotes());
        return jobApplicationRepository.save(existing);
    }

    public void delete(Long id, Long userId) {
        JobApplication existing = getOwnedOrThrow(id, userId);
        jobApplicationRepository.delete(existing);
    }

    private JobApplication getOwnedOrThrow(Long id, Long userId) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        if (!application.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Job application not found with id: " + id);
        }
        return application;
    }
}
