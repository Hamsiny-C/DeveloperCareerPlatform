package com.devcareeros.service;

import com.devcareeros.dto.DashboardSummary;
import com.devcareeros.entity.*;
import com.devcareeros.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * This service GATHERS data from every other module's repository and
 * combines it into one DashboardSummary object. This is exactly the kind
 * of thing a Service layer is good for: pulling together information from
 * multiple sources to answer one question ("how is my career prep going?").
 */
@Service
public class DashboardService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private DsaProblemRepository dsaProblemRepository;

    @Autowired
    private LearningTopicRepository learningTopicRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    public DashboardSummary buildSummary(Long userId) {
        DashboardSummary summary = new DashboardSummary();

        List<Skill> skills = skillRepository.findByUserId(userId);
        List<LearningTopic> topics = learningTopicRepository.findByUserId(userId);
        List<DsaProblem> dsaProblems = dsaProblemRepository.findByUserId(userId);
        List<Interview> interviews = interviewRepository.findByUserId(userId);

        // ---- Skills ----
        double avgSkill = skills.stream()
                .mapToInt(Skill::getProgressPercentage)
                .average()
                .orElse(0);
        summary.setAverageSkillProgress(Math.round(avgSkill * 10.0) / 10.0);

        Map<String, Integer> skillMap = new LinkedHashMap<>();
        for (Skill s : skills) {
            skillMap.put(s.getName(), s.getProgressPercentage());
        }
        summary.setSkillProgressMap(skillMap);

        // ---- Learning ----
        double avgLearning = topics.stream()
                .mapToInt(LearningTopic::getProgressPercentage)
                .average()
                .orElse(0);
        summary.setAverageLearningProgress(Math.round(avgLearning * 10.0) / 10.0);

        // ---- DSA ----
        summary.setTotalDsaProblems(dsaProblems.size());
        summary.setDsaSolved(dsaProblemRepository.countByUserIdAndStatus(userId, "SOLVED"));
        summary.setDsaEasy(dsaProblemRepository.countByUserIdAndDifficulty(userId, "EASY"));
        summary.setDsaMedium(dsaProblemRepository.countByUserIdAndDifficulty(userId, "MEDIUM"));
        summary.setDsaHard(dsaProblemRepository.countByUserIdAndDifficulty(userId, "HARD"));

        // ---- Projects / Certifications / Applications ----
        summary.setTotalProjects(projectRepository.findByUserId(userId).size());
        summary.setTotalCertifications(certificationRepository.findByUserId(userId).size());
        summary.setTotalApplications(jobApplicationRepository.countByUserId(userId));

        // ---- Upcoming interviews (today or later) ----
        long upcoming = interviews.stream()
                .filter(i -> i.getInterviewDate() != null && !i.getInterviewDate().isBefore(LocalDate.now()))
                .count();
        summary.setUpcomingInterviews(upcoming);

        // ---- Overall career progress ----
        // A simple, transparent weighted average across the main pillars.
        // Feel free to tweak these weights once you understand the app.
        double dsaScore = dsaProblems.isEmpty() ? 0
                : (100.0 * summary.getDsaSolved() / dsaProblems.size());

        double overall = (avgSkill * 0.35) + (avgLearning * 0.25) + (dsaScore * 0.25)
                + (Math.min(summary.getTotalProjects(), 5) * 4); // up to +20 for having projects
        summary.setCareerProgressPercentage((int) Math.min(100, Math.round(overall)));

        // ---- Recent activity feed (very simple: latest DSA + latest applications) ----
        List<Map<String, Object>> activity = new ArrayList<>();
        dsaProblems.stream()
                .filter(p -> "SOLVED".equalsIgnoreCase(p.getStatus()) && p.getDateSolved() != null)
                .sorted(Comparator.comparing(DsaProblem::getDateSolved).reversed())
                .limit(5)
                .forEach(p -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("type", "DSA");
                    item.put("description", "Solved: " + p.getProblemName());
                    item.put("date", p.getDateSolved());
                    activity.add(item);
                });
        summary.setRecentActivities(activity);

        return summary;
    }
}
