package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.Timeline;
import com.portfolio.service.TimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio-api/admin/timeline")
@RequiredArgsConstructor
public class AdminTimelineController {

    private final TimelineService timelineService;

    @GetMapping
    public Result<List<Timeline>> list() {
        return Result.success(timelineService.getAll());
    }

    @PostMapping
    public Result<Timeline> create(@RequestBody Timeline timeline) {
        return Result.success(timelineService.create(timeline));
    }

    @PutMapping("/{id}")
    public Result<Timeline> update(@PathVariable Long id, @RequestBody Timeline timeline) {
        try {
            return Result.success(timelineService.update(id, timeline));
        } catch (RuntimeException e) {
            return Result.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        timelineService.delete(id);
        return Result.success();
    }
}
