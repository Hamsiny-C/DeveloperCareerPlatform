package com.devcareeros.repository;

import com.devcareeros.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
    long countByUserId(Long userId);
    long countByUserIdAndStatus(Long userId, String status);
}
