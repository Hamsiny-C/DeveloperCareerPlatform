package com.devcareeros.dto;

import java.util.List;
import java.util.Map;

/**
 * Everything the Dashboard page needs, bundled into ONE response so the
 * frontend only has to make a single API call to /api/dashboard/summary.
 */
public class DashboardSummary {

    private int careerProgressPercentage;

    private int totalDsaProblems;
    private long dsaSolved;
    private long dsaEasy;
    private long dsaMedium;
    private long dsaHard;

    private double averageSkillProgress;
    private double averageLearningProgress;

    private long totalProjects;
    private long totalCertifications;
    private long totalApplications;
    private long upcomingInterviews;

    private Map<String, Integer> skillProgressMap; // skillName -> percentage
    private List<Map<String, Object>> recentActivities;

    public int getCareerProgressPercentage() {
        return careerProgressPercentage;
    }

    public void setCareerProgressPercentage(int careerProgressPercentage) {
        this.careerProgressPercentage = careerProgressPercentage;
    }

    public int getTotalDsaProblems() {
        return totalDsaProblems;
    }

    public void setTotalDsaProblems(int totalDsaProblems) {
        this.totalDsaProblems = totalDsaProblems;
    }

    public long getDsaSolved() {
        return dsaSolved;
    }

    public void setDsaSolved(long dsaSolved) {
        this.dsaSolved = dsaSolved;
    }

    public long getDsaEasy() {
        return dsaEasy;
    }

    public void setDsaEasy(long dsaEasy) {
        this.dsaEasy = dsaEasy;
    }

    public long getDsaMedium() {
        return dsaMedium;
    }

    public void setDsaMedium(long dsaMedium) {
        this.dsaMedium = dsaMedium;
    }

    public long getDsaHard() {
        return dsaHard;
    }

    public void setDsaHard(long dsaHard) {
        this.dsaHard = dsaHard;
    }

    public double getAverageSkillProgress() {
        return averageSkillProgress;
    }

    public void setAverageSkillProgress(double averageSkillProgress) {
        this.averageSkillProgress = averageSkillProgress;
    }

    public double getAverageLearningProgress() {
        return averageLearningProgress;
    }

    public void setAverageLearningProgress(double averageLearningProgress) {
        this.averageLearningProgress = averageLearningProgress;
    }

    public long getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(long totalProjects) {
        this.totalProjects = totalProjects;
    }

    public long getTotalCertifications() {
        return totalCertifications;
    }

    public void setTotalCertifications(long totalCertifications) {
        this.totalCertifications = totalCertifications;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getUpcomingInterviews() {
        return upcomingInterviews;
    }

    public void setUpcomingInterviews(long upcomingInterviews) {
        this.upcomingInterviews = upcomingInterviews;
    }

    public Map<String, Integer> getSkillProgressMap() {
        return skillProgressMap;
    }

    public void setSkillProgressMap(Map<String, Integer> skillProgressMap) {
        this.skillProgressMap = skillProgressMap;
    }

    public List<Map<String, Object>> getRecentActivities() {
        return recentActivities;
    }

    public void setRecentActivities(List<Map<String, Object>> recentActivities) {
        this.recentActivities = recentActivities;
    }
}
