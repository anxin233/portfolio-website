package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.ValueItem;
import com.portfolio.service.ValueItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio-api/values")
@RequiredArgsConstructor
public class ValueItemController {

    private final ValueItemService valueItemService;

    @GetMapping
    public Result<List<ValueItem>> list() {
        return Result.success(valueItemService.getAll());
    }
}
