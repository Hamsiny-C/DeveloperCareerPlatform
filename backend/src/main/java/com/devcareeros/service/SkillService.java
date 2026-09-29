package com.devcareeros.service;

import com.devcareeros.entity.Skill;
import com.devcareeros.entity.User;
import com.devcareeros.exception.ResourceNotFoundException;
import com.devcareeros.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getAllForUser(Long userId) {
        return skillRepository.findByUserId(userId);
    }

    public Skill create(Skill skill, User user) {
        skill.setUser(user);
        return skillRepository.save(skill);
    }

    public Skill update(Long id, Skill updated, Long userId) {
        Skill existing = getOwnedSkillOrThrow(id, userId);
        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setLevel(updated.getLevel());
        existing.setProgressPercentage(updated.getProgressPercentage());
        return skillRepository.save(existing);
    }

    public void delete(Long id, Long userId) {
        Skill existing = getOwnedSkillOrThrow(id, userId);
        skillRepository.delete(existing);
    }

    // Makes sure the skill exists AND belongs to the currently logged-in
    // user - this stops User A from editing/deleting User B's data just by
    // guessing an id in the URL.
    private Skill getOwnedSkillOrThrow(Long id, Long userId) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
        if (!skill.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        return skill;
    }
}
