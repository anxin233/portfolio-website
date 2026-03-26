package com.portfolio.service;

import com.portfolio.entity.BlogPost;
import com.portfolio.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogPostRepository repository;

    public List<BlogPost> getAllPublished() {
        return repository.findByPublishedTrueOrderByCreatedAtDesc();
    }

    public Page<BlogPost> getPublishedPaged(int page, int size) {
        return repository.findByPublishedTrue(
            PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
    }

    public List<BlogPost> getByCategory(String category) {
        return repository.findByCategoryAndPublishedTrueOrderByCreatedAtDesc(category);
    }

    public List<BlogPost> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Page<BlogPost> getAllPaged(int page, int size) {
        return repository.findAll(
            PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
    }

    public Optional<BlogPost> getById(Long id) {
        return repository.findById(id);
    }

    public BlogPost togglePublish(Long id) {
        return repository.findById(id)
            .map(post -> {
                post.setPublished(!post.getPublished());
                return repository.save(post);
            })
            .orElseThrow(() -> new RuntimeException("Blog post not found: " + id));
    }

    public BlogPost create(BlogPost post) {
        return repository.save(post);
    }

    public BlogPost update(Long id, BlogPost updated) {
        return repository.findById(id)
            .map(existing -> {
                existing.setTitle(updated.getTitle());
                existing.setExcerpt(updated.getExcerpt());
                existing.setContent(updated.getContent());
                existing.setCategory(updated.getCategory());
                existing.setTags(updated.getTags());
                existing.setReadTime(updated.getReadTime());
                existing.setPublished(updated.getPublished());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Blog post not found: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
