package com.portfolio.service;

import com.portfolio.entity.ValueItem;
import com.portfolio.repository.ValueItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValueItemService {

    private final ValueItemRepository repository;

    public List<ValueItem> getAll() {
        return repository.findAllByOrderBySortOrderAscIdAsc();
    }

    public ValueItem create(ValueItem item) {
        return repository.save(item);
    }

    public ValueItem update(Long id, ValueItem updated) {
        return repository.findById(id)
            .map(existing -> {
                existing.setIcon(updated.getIcon());
                existing.setTitle(updated.getTitle());
                existing.setDescription(updated.getDescription());
                existing.setSortOrder(updated.getSortOrder());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("理念不存在: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
