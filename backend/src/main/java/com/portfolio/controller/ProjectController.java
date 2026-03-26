package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.Project;
import com.portfolio.entity.ProjectChallenge;
import com.portfolio.service.ProjectChallengeService;
import com.portfolio.service.ProjectService;
import com.portfolio.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portfolio-api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectChallengeService challengeService;
    private final SiteConfigService siteConfigService;

    @GetMapping
    public Result<List<Project>> list() {
        return Result.success(projectService.getAll());
    }

    @GetMapping("/challenges")
    public Result<List<ProjectChallenge>> challenges() {
        return Result.success(challengeService.getAll());
    }

    @GetMapping("/featured")
    public Result<Map<String, String>> featured() {
        Map<String, String> all = siteConfigService.getAll();
        Map<String, String> featured = new HashMap<>();
        all.forEach((k, v) -> {
            if (k.startsWith("featured_")) {
                featured.put(k, v);
            }
        });
        return Result.success(featured);
    }
}
