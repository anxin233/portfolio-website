package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.Skill;
import com.portfolio.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio-api/admin/skills")
@RequiredArgsConstructor
public class AdminSkillController {

    private final SkillService skillService;

    @GetMapping
    public Result<List<Skill>> list() {
        return Result.success(skillService.getAll());
    }

    @PostMapping
    public Result<Skill> create(@RequestBody Skill skill) {
        return Result.success(skillService.create(skill));
    }

    @PutMapping("/{id}")
    public Result<Skill> update(@PathVariable Long id, @RequestBody Skill skill) {
        try {
            return Result.success(skillService.update(id, skill));
        } catch (RuntimeException e) {
            return Result.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        skillService.delete(id);
        return Result.success();
    }
}
