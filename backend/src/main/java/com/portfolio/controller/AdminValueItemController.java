package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.entity.ValueItem;
import com.portfolio.service.ValueItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio-api/admin/values")
@RequiredArgsConstructor
public class AdminValueItemController {

    private final ValueItemService valueItemService;

    @GetMapping
    public Result<List<ValueItem>> list() {
        return Result.success(valueItemService.getAll());
    }

    @PostMapping
    public Result<ValueItem> create(@RequestBody ValueItem item) {
        return Result.success(valueItemService.create(item));
    }

    @PutMapping("/{id}")
    public Result<ValueItem> update(@PathVariable Long id, @RequestBody ValueItem item) {
        try {
            return Result.success(valueItemService.update(id, item));
        } catch (RuntimeException e) {
            return Result.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        valueItemService.delete(id);
        return Result.success();
    }
}
