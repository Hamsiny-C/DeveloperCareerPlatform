package com.devcareeros.service;

import com.devcareeros.entity.Interview;
import com.devcareeros.entity.User;
import com.devcareeros.exception.ResourceNotFoundException;
import com.devcareeros.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    public List<Interview> getAllForUser(Long userId) {
        return interviewRepository.findByUserId(userId);
    }

    public Interview create(Interview interview, User user) {
        interview.setUser(user);
        return interviewRepository.save(interview);
    }

    public Interview update(Long id, Interview updated, Long userId) {
        Interview existing = getOwnedOrThrow(id, userId);
        existing.setCompany(updated.getCompany());
        existing.setRole(updated.getRole());
        existing.setInterviewDate(updated.getInterviewDate());
        existing.setInterviewType(updated.getInterviewType());
        existing.setRound(updated.getRound());
        existing.setResult(updated.getResult());
        existing.setNotes(updated.getNotes());
        return interviewRepository.save(existing);
    }

    public void delete(Long id, Long userId) {
        Interview existing = getOwnedOrThrow(id, userId);
        interviewRepository.delete(existing);
    }

    private Interview getOwnedOrThrow(Long id, Long userId) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found with id: " + id));
        if (!interview.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Interview not found with id: " + id);
        }
        return interview;
    }
}
