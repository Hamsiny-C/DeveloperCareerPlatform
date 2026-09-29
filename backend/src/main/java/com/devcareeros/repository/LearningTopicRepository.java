package com.devcareeros.repository;

import com.devcareeros.entity.LearningTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LearningTopicRepository extends JpaRepository<LearningTopic, Long> {
    List<LearningTopic> findByUserId(Long userId);
}
