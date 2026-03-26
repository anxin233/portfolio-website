package com.portfolio.service;

import com.portfolio.entity.Category;
import com.portfolio.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> getAll() {
        return repository.findAllByOrderBySortOrderAscIdAsc();
    }

    public Category create(Category category) {
        if (repository.existsByName(category.getName())) {
            throw new RuntimeException("分类已存在: " + category.getName());
        }
        return repository.save(category);
    }

    public Category update(Long id, Category updated) {
        return repository.findById(id)
            .map(existing -> {
                existing.setName(updated.getName());
                existing.setSortOrder(updated.getSortOrder());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("分类不存在: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<Category> getByName(String name) {
        return repository.findByName(name);
    }
}
