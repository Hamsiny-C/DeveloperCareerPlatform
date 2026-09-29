package com.devcareeros.service;

import com.devcareeros.entity.DsaProblem;
import com.devcareeros.entity.User;
import com.devcareeros.exception.ResourceNotFoundException;
import com.devcareeros.repository.DsaProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DsaProblemService {

    @Autowired
    private DsaProblemRepository dsaProblemRepository;

    public List<DsaProblem> getAllForUser(Long userId) {
        return dsaProblemRepository.findByUserId(userId);
    }

    public DsaProblem create(DsaProblem problem, User user) {
        problem.setUser(user);
        return dsaProblemRepository.save(problem);
    }

    public DsaProblem update(Long id, DsaProblem updated, Long userId) {
        DsaProblem existing = getOwnedOrThrow(id, userId);
        existing.setProblemName(updated.getProblemName());
        existing.setTopic(updated.getTopic());
        existing.setDifficulty(updated.getDifficulty());
        existing.setPlatform(updated.getPlatform());
        existing.setStatus(updated.getStatus());
        existing.setDateSolved(updated.getDateSolved());
        existing.setNotes(updated.getNotes());
        return dsaProblemRepository.save(existing);
    }

    public void delete(Long id, Long userId) {
        DsaProblem existing = getOwnedOrThrow(id, userId);
        dsaProblemRepository.delete(existing);
    }

    private DsaProblem getOwnedOrThrow(Long id, Long userId) {
        DsaProblem problem = dsaProblemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DSA problem not found with id: " + id));
        if (!problem.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("DSA problem not found with id: " + id);
        }
        return problem;
    }
}
