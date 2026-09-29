package com.devcareeros.repository;

import com.devcareeros.entity.DsaProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DsaProblemRepository extends JpaRepository<DsaProblem, Long> {
    List<DsaProblem> findByUserId(Long userId);
    long countByUserIdAndStatus(Long userId, String status);
    long countByUserIdAndDifficulty(Long userId, String difficulty);
}
