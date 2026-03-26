package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portfolio-api/site-config")
@RequiredArgsConstructor
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    @GetMapping
    public Result<Map<String, String>> getAll() {
        return Result.success(siteConfigService.getAll());
    }
}
