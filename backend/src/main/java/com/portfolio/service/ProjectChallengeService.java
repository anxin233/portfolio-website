package com.portfolio.service;

import com.portfolio.entity.ProjectChallenge;
import com.portfolio.repository.ProjectChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectChallengeService {

    private final ProjectChallengeRepository repository;

    public List<ProjectChallenge> getAll() {
        return repository.findAllByOrderBySortOrderAscIdAsc();
    }

    public ProjectChallenge create(ProjectChallenge challenge) {
        return repository.save(challenge);
    }

    public ProjectChallenge update(Long id, ProjectChallenge updated) {
        return repository.findById(id)
            .map(existing -> {
                existing.setTitle(updated.getTitle());
                existing.setProblem(updated.getProblem());
                existing.setSolution(updated.getSolution());
                existing.setResult(updated.getResult());
                existing.setSortOrder(updated.getSortOrder());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("技术难点不存在: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
