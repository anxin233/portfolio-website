package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.Project;
import com.portfolio.entity.ProjectChallenge;
import com.portfolio.service.ProjectChallengeService;
import com.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio-api/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {

    private final ProjectService projectService;
    private final ProjectChallengeService challengeService;

    @GetMapping
    public Result<List<Project>> list() {
        return Result.success(projectService.getAll());
    }

    @PostMapping
    public Result<Project> create(@RequestBody Project project) {
        return Result.success(projectService.create(project));
    }

    @PutMapping("/{id}")
    public Result<Project> update(@PathVariable Long id, @RequestBody Project project) {
        try {
            return Result.success(projectService.update(id, project));
        } catch (RuntimeException e) {
            return Result.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return Result.success();
    }

    @GetMapping("/challenges")
    public Result<List<ProjectChallenge>> challenges() {
        return Result.success(challengeService.getAll());
    }

    @PostMapping("/challenges")
    public Result<ProjectChallenge> createChallenge(@RequestBody ProjectChallenge challenge) {
        return Result.success(challengeService.create(challenge));
    }

    @PutMapping("/challenges/{id}")
    public Result<ProjectChallenge> updateChallenge(@PathVariable Long id, @RequestBody ProjectChallenge challenge) {
        try {
            return Result.success(challengeService.update(id, challenge));
        } catch (RuntimeException e) {
            return Result.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/challenges/{id}")
    public Result<Void> deleteChallenge(@PathVariable Long id) {
        challengeService.delete(id);
        return Result.success();
    }
}
