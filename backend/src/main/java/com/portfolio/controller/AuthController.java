package com.portfolio.controller;

import com.portfolio.common.Result;
import com.portfolio.common.TokenUtil;
import com.portfolio.entity.AdminUser;
import com.portfolio.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminUserRepository adminUserRepository;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null) {
            return Result.error(400, "用户名和密码不能为空");
        }

        return adminUserRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(TokenUtil.hashPassword(password)))
                .map(user -> {
                    String token = TokenUtil.generateToken(user.getId());
                    user.setLastLoginAt(LocalDateTime.now());
                    adminUserRepository.save(user);

                    Map<String, Object> data = new HashMap<>();
                    data.put("token", token);
                    data.put("nickname", user.getNickname());
                    data.put("username", user.getUsername());
                    return Result.success(data);
                })
                .orElse(Result.error(401, "用户名或密码错误"));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            TokenUtil.invalidate(authHeader.substring(7));
        }
        return Result.success();
    }

    @GetMapping("/check")
    public Result<Void> check(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ") && TokenUtil.validate(authHeader.substring(7))) {
            return Result.success();
        }
        return Result.error(401, "未登录或登录已过期");
    }
}
