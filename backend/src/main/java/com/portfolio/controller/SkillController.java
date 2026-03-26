package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.Skill;
import com.portfolio.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio-api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public Result<List<Skill>> list() {
        return Result.success(skillService.getAll());
    }
}
