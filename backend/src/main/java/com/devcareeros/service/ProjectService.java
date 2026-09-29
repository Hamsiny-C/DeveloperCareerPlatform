package com.devcareeros.service;

import com.devcareeros.entity.Project;
import com.devcareeros.entity.User;
import com.devcareeros.exception.ResourceNotFoundException;
import com.devcareeros.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllForUser(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    public Project create(Project project, User user) {
        project.setUser(user);
        return projectRepository.save(project);
    }

    public Project update(Long id, Project updated, Long userId) {
        Project existing = getOwnedOrThrow(id, userId);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setTechnologies(updated.getTechnologies());
        existing.setGithubUrl(updated.getGithubUrl());
        existing.setLiveUrl(updated.getLiveUrl());
        existing.setStatus(updated.getStatus());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        return projectRepository.save(existing);
    }

    public void delete(Long id, Long userId) {
        Project existing = getOwnedOrThrow(id, userId);
        projectRepository.delete(existing);
    }

    private Project getOwnedOrThrow(Long id, Long userId) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        if (!project.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        return project;
    }
}
