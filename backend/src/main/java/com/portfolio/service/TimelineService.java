package com.portfolio.service;

import com.portfolio.entity.Timeline;
import com.portfolio.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineService {

    private final TimelineRepository repository;

    public List<Timeline> getAll() {
        return repository.findAllByOrderBySortOrderAscIdAsc();
    }

    public Timeline create(Timeline timeline) {
        return repository.save(timeline);
    }

    public Timeline update(Long id, Timeline updated) {
        return repository.findById(id)
            .map(existing -> {
                existing.setYear(updated.getYear());
                existing.setTitle(updated.getTitle());
                existing.setDescription(updated.getDescription());
                existing.setTechs(updated.getTechs());
                existing.setSortOrder(updated.getSortOrder());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("历程不存在: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
