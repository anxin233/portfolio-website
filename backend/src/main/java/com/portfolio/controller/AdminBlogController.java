package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.BlogPost;
import com.portfolio.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/blog")
@RequiredArgsConstructor
public class AdminBlogController {

    private final BlogService blogService;

    @GetMapping
    public Result<List<BlogPost>> listAll() {
        return Result.success(blogService.getAll());
    }

    @GetMapping("/page")
    public Result<Page<BlogPost>> listPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(blogService.getAllPaged(page, size));
    }

    @GetMapping("/{id}")
    public Result<BlogPost> detail(@PathVariable Long id) {
        return blogService.getById(id)
                .map(Result::success)
                .orElse(Result.error(404, "文章不存在"));
    }

    @PostMapping
    public Result<BlogPost> create(@RequestBody BlogPost post) {
        return Result.success(blogService.create(post));
    }

    @PutMapping("/{id}")
    public Result<BlogPost> update(@PathVariable Long id, @RequestBody BlogPost post) {
        return Result.success(blogService.update(id, post));
    }

    @PutMapping("/{id}/publish")
    public Result<BlogPost> togglePublish(@PathVariable Long id) {
        return Result.success(blogService.togglePublish(id));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        blogService.delete(id);
        return Result.success();
    }
}
