package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portfolio-api/admin/site-config")
@RequiredArgsConstructor
public class AdminSiteConfigController {

    private final SiteConfigService siteConfigService;

    @GetMapping
    public Result<Map<String, String>> getAll() {
        return Result.success(siteConfigService.getAll());
    }

    @PutMapping
    public Result<Void> saveAll(@RequestBody Map<String, String> configs) {
        siteConfigService.saveAll(configs);
        return Result.success();
    }
}
