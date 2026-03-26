package com.portfolio.config;

import com.portfolio.common.TokenUtil;
import com.portfolio.entity.AdminUser;
import com.portfolio.entity.BlogPost;
import com.portfolio.repository.AdminUserRepository;
import com.portfolio.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BlogPostRepository blogPostRepository;
    private final AdminUserRepository adminUserRepository;

    @Override
    public void run(String... args) {
        initAdmin();
        if (blogPostRepository.count() > 0) return;

        createPost(
            "Vue 2 到 Vue 3 迁移实战：渐进式策略与踩坑记录",
            "分享在生产项目中从 Vue 2 逐步迁移到 Vue 3 的完整策略。",
            "## 背景\n\n在我们的轮胎PLM系统中，前端最初基于Vue 2 + Vuex构建...",
            "前端",
            "Vue 3,Migration,Composition API",
            12
        );

        createPost(
            "企业级前端架构设计：从项目结构到团队协作",
            "基于轮胎PLM系统的实战经验，总结大型B端前端项目的架构设计原则。",
            "## 为什么需要前端架构\n\n当项目超过10个业务模块时...",
            "架构",
            "Architecture,Engineering,Vue",
            15
        );

        createPost(
            "Spring Boot RESTful API 设计中的实践与反思",
            "在参与后端开发过程中积累的 API 设计经验。",
            "## 统一响应格式\n\n一个好的API应该有一致的响应结构...",
            "后端",
            "Spring Boot,REST,Java",
            10
        );
    }

    private void initAdmin() {
        if (adminUserRepository.count() == 0) {
            AdminUser admin = new AdminUser();
            admin.setUsername("admin");
            admin.setPassword(TokenUtil.hashPassword("admin123"));
            admin.setNickname("管理员");
            adminUserRepository.save(admin);
            log.info(">>> 默认管理员账号已创建：admin / admin123");
        }
    }

    private void createPost(String title, String excerpt, String content,
                            String category, String tags, int readTime) {
        BlogPost post = new BlogPost();
        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setContent(content);
        post.setCategory(category);
        post.setTags(tags);
        post.setReadTime(readTime);
        post.setPublished(true);
        blogPostRepository.save(post);
    }
}
