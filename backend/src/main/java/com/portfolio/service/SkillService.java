package com.portfolio.service;

import com.portfolio.entity.Skill;
import com.portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository repository;

    public List<Skill> getAll() {
        return repository.findAllByOrderBySortOrderAscIdAsc();
    }

    public Skill create(Skill skill) {
        return repository.save(skill);
    }

    public Skill update(Long id, Skill updated) {
        return repository.findById(id)
            .map(existing -> {
                existing.setName(updated.getName());
                existing.setLevel(updated.getLevel());
                existing.setDescription(updated.getDescription());
                existing.setSortOrder(updated.getSortOrder());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("技能不存在: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
