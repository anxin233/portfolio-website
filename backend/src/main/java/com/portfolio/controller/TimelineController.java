package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.Timeline;
import com.portfolio.service.TimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio-api/timeline")
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineService timelineService;

    @GetMapping
    public Result<List<Timeline>> list() {
        return Result.success(timelineService.getAll());
    }
}
