package com.portfolio.service;

import com.portfolio.entity.Project;
import com.portfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public List<Project> getAll() {
        return repository.findAllByOrderBySortOrderAscIdAsc();
    }

    public Project create(Project project) {
        return repository.save(project);
    }

    public Project update(Long id, Project updated) {
        return repository.findById(id)
            .map(existing -> {
                existing.setIcon(updated.getIcon());
                existing.setTitle(updated.getTitle());
                existing.setDescription(updated.getDescription());
                existing.setTechs(updated.getTechs());
                existing.setGithub(updated.getGithub());
                existing.setDemo(updated.getDemo());
                existing.setSortOrder(updated.getSortOrder());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("项目不存在: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
