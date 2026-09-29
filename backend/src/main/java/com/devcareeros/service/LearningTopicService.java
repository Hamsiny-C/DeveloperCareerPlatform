package com.devcareeros.service;

import com.devcareeros.entity.LearningTopic;
import com.devcareeros.entity.User;
import com.devcareeros.exception.ResourceNotFoundException;
import com.devcareeros.repository.LearningTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningTopicService {

    @Autowired
    private LearningTopicRepository learningTopicRepository;

    public List<LearningTopic> getAllForUser(Long userId) {
        return learningTopicRepository.findByUserId(userId);
    }

    public LearningTopic create(LearningTopic topic, User user) {
        topic.setUser(user);
        return learningTopicRepository.save(topic);
    }

    public LearningTopic update(Long id, LearningTopic updated, Long userId) {
        LearningTopic existing = getOwnedOrThrow(id, userId);
        existing.setTopic(updated.getTopic());
        existing.setCategory(updated.getCategory());
        existing.setProgressPercentage(updated.getProgressPercentage());
        existing.setStatus(updated.getStatus());
        existing.setNotes(updated.getNotes());
        return learningTopicRepository.save(existing);
    }

    public void delete(Long id, Long userId) {
        LearningTopic existing = getOwnedOrThrow(id, userId);
        learningTopicRepository.delete(existing);
    }

    private LearningTopic getOwnedOrThrow(Long id, Long userId) {
        LearningTopic topic = learningTopicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning topic not found with id: " + id));
        if (!topic.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Learning topic not found with id: " + id);
        }
        return topic;
    }
}
