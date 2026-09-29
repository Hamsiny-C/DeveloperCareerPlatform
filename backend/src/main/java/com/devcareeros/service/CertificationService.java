package com.devcareeros.service;

import com.devcareeros.entity.Certification;
import com.devcareeros.entity.User;
import com.devcareeros.exception.ResourceNotFoundException;
import com.devcareeros.repository.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    public List<Certification> getAllForUser(Long userId) {
        return certificationRepository.findByUserId(userId);
    }

    public Certification create(Certification certification, User user) {
        certification.setUser(user);
        return certificationRepository.save(certification);
    }

    public Certification update(Long id, Certification updated, Long userId) {
        Certification existing = getOwnedOrThrow(id, userId);
        existing.setName(updated.getName());
        existing.setProvider(updated.getProvider());
        existing.setIssueDate(updated.getIssueDate());
        existing.setCredentialId(updated.getCredentialId());
        existing.setCredentialUrl(updated.getCredentialUrl());
        existing.setStatus(updated.getStatus());
        return certificationRepository.save(existing);
    }

    public void delete(Long id, Long userId) {
        Certification existing = getOwnedOrThrow(id, userId);
        certificationRepository.delete(existing);
    }

    private Certification getOwnedOrThrow(Long id, Long userId) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found with id: " + id));
        if (!certification.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Certification not found with id: " + id);
        }
        return certification;
    }
}
