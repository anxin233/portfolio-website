package com.portfolio.service;

import com.portfolio.entity.SiteConfig;
import com.portfolio.repository.SiteConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteConfigService {

    private final SiteConfigRepository repository;

    public Map<String, String> getAll() {
        return repository.findAll().stream()
            .collect(Collectors.toMap(SiteConfig::getConfigKey, c -> c.getConfigValue() != null ? c.getConfigValue() : ""));
    }

    public String get(String key) {
        return repository.findByConfigKey(key)
            .map(SiteConfig::getConfigValue)
            .orElse("");
    }

    public void save(String key, String value) {
        SiteConfig config = repository.findByConfigKey(key)
            .orElseGet(() -> {
                SiteConfig c = new SiteConfig();
                c.setConfigKey(key);
                return c;
            });
        config.setConfigValue(value);
        repository.save(config);
    }

    public void saveAll(Map<String, String> configs) {
        configs.forEach(this::save);
    }
}
