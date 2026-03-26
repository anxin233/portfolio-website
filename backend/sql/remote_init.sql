SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS `portfolio_db`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE `portfolio_db`;

-- 博客文章表
CREATE TABLE IF NOT EXISTS `blog_post` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title`      VARCHAR(255) NOT NULL                COMMENT '文章标题',
  `excerpt`    VARCHAR(500) DEFAULT NULL             COMMENT '文章摘要',
  `content`    TEXT                                  COMMENT '文章内容',
  `category`   VARCHAR(50)  NOT NULL                COMMENT '分类',
  `tags`       VARCHAR(255) DEFAULT NULL             COMMENT '标签',
  `read_time`  INT          DEFAULT NULL             COMMENT '阅读时间',
  `published`  TINYINT(1)   NOT NULL DEFAULT 0       COMMENT '是否发布',
  `created_at` DATETIME     DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_category` (`category`),
  INDEX `idx_published` (`published`),
  INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 管理员表
CREATE TABLE IF NOT EXISTS `admin_user` (
  `id`            BIGINT       NOT NULL AUTO_INCREMENT,
  `username`      VARCHAR(50)  NOT NULL UNIQUE,
  `password`      VARCHAR(255) NOT NULL,
  `nickname`      VARCHAR(50)  DEFAULT NULL,
  `last_login_at` DATETIME     DEFAULT NULL,
  `created_at`    DATETIME     DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 清空旧数据（如有）
DELETE FROM `blog_post`;
DELETE FROM `admin_user`;
ALTER TABLE `blog_post` AUTO_INCREMENT = 1;
ALTER TABLE `admin_user` AUTO_INCREMENT = 1;

-- 管理员 admin / admin123
INSERT INTO `admin_user` (`username`, `password`, `nickname`) VALUES
('admin', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', '管理员');

-- 博客文章
INSERT INTO `blog_post` (`title`, `excerpt`, `content`, `category`, `tags`, `read_time`, `published`) VALUES
(
  'Vue 2 到 Vue 3 迁移实战：渐进式策略与踩坑记录',
  '分享在生产项目中从 Vue 2 逐步迁移到 Vue 3 的完整策略，包括 Composition API 改造、状态管理切换、以及如何在不停业务的前提下平稳过渡。',
  '## 背景\n\n在我们的轮胎PLM系统中，前端最初基于Vue 2 + Vuex构建。随着Vue 3的成熟和Composition API带来的代码组织优势，我们决定进行渐进式迁移。但在一个正在运行的生产系统上做技术迁移，最大的挑战是：**不能停业务**。\n\n## 迁移策略\n\n我们没有选择一次性重写，而是制定了三阶段渐进策略：\n\n### 阶段一：基础设施升级\n- 将Vue版本升级到2.7（支持Composition API）\n- 引入script setup语法，新模块优先使用\n- 保持Vuex不变，渐进引入Pinia\n\n### 阶段二：新模块全面Vue 3\n- 新业务模块使用Vue 3 + Composition API\n- 公共组件层做兼容封装\n- 建立新的代码规范文档\n\n### 阶段三：老模块逐步替换\n- 按业务优先级排列迁移清单\n- 每个迭代安排20%的技术改造工时\n- 添加自动化测试覆盖改造模块\n\n## 总结\n\n渐进式迁移的核心是**可控**和**可回滚**。每一步都应该是可以独立上线、独立回退的。',
  '前端',
  'Vue 3,Migration,Composition API',
  12,
  1
),
(
  '企业级前端架构设计：从项目结构到团队协作',
  '基于轮胎PLM系统的实战经验，总结大型B端前端项目的架构设计原则：模块划分、状态管理、组件抽象、以及工程化规范。',
  '## 为什么需要前端架构\n\n当项目超过10个业务模块、开发人员超过3人时，没有架构约束的前端代码会迅速劣化。在PLM系统中，我从零搭建了一套适合团队协作的前端架构。\n\n## 目录结构设计\n\n一个好的目录结构应该让人看目录就知道这个项目做什么。\n\n## 组件设计原则\n\n### 三层组件体系\n1. **基础组件**：对UI库的二次封装，统一API\n2. **业务组件**：特定领域的复合组件\n3. **页面组件**：路由对应的容器组件\n\n## 总结\n\n前端架构不是一次性的工作，它需要随着业务演进持续调整。关键是建立**约定**和**自动化**，让正确的做法成为最省力的做法。',
  '架构',
  'Architecture,Engineering,Vue',
  15,
  1
),
(
  'Spring Boot RESTful API 设计中的实践与反思',
  '在参与后端开发过程中积累的 API 设计经验：统一响应格式、异常处理、接口版本管理与前后端协作约定。',
  '## 统一响应格式\n\n一个好的API应该有一致的响应结构，让前端可以用统一的方式处理所有接口返回。\n\n## 异常处理\n\n通过全局异常处理器统一捕获异常，避免在每个Controller中重复try-catch。\n\n## 接口版本管理\n\n当API需要不兼容的变更时，通过URL路径版本号来管理。\n\n## 总结\n\n好的API设计是前后端高效协作的基础。统一的约定比完美的技术方案更重要。',
  '后端',
  'Spring Boot,REST,Java',
  10,
  1
),
(
  '从开发者到IT主管：技术管理的第一年',
  '记录从纯技术角色转型为10人团队管理者的心路历程，包括如何平衡技术与管理、建立团队规范、以及跨部门沟通的技巧。',
  '## 角色转变\n\n从写代码到带团队，最大的变化不是工作内容，而是**思维方式**。\n\n## 平衡技术与管理\n\n作为技术出身的管理者，最容易犯的错误是事事亲力亲为。学会放手和信任团队是必修课。\n\n## 跨部门沟通\n\n与业务部门沟通时，最重要的是学会用对方的语言描述技术方案的价值。\n\n## 总结\n\n管理不是放弃技术，而是用更高的视角来运用技术解决问题。',
  '团队',
  'Management,Leadership,Growth',
  8,
  1
),
(
  '复杂表单系统的设计思路：配置化 vs 组件化',
  '在PLM系统中面对上百个业务表单时，如何选择合适的抽象层级？对比配置化驱动和组件化方案的优劣。',
  '## 问题背景\n\nPLM系统有大量的业务表单，字段多、校验规则复杂、且经常变化。如何高效地开发和维护这些表单？\n\n## 方案一：JSON Schema配置化\n\n通过JSON描述表单结构，由渲染引擎动态生成。\n\n优点：新增表单快，非开发人员也能配置。\n缺点：复杂交互难以描述，调试困难。\n\n## 方案二：组件化封装\n\n基于Vue组件体系，抽取通用的表单组件。\n\n优点：灵活度高，TypeScript类型安全。\n缺点：需要开发人员编码，复用度取决于抽象水平。\n\n## 我们的选择\n\n最终采用了**混合方案**：简单表单用配置化，复杂表单用组件化，两者共享校验规则库。\n\n## 总结\n\n没有银弹，选择取决于团队能力和业务特点。',
  '前端',
  'Form Design,Vue,Architecture',
  11,
  1
),
(
  '前端性能优化实战：首屏加载从 5s 到 1.5s',
  '在企业内网环境下，通过路由懒加载、组件按需引入、打包分析与CDN策略，将首屏加载时间压缩 70%。',
  '## 现状分析\n\n项目打包后总体积超过3MB，首屏加载需要5秒以上。在企业内网环境下，带宽有限，体验很差。\n\n## 优化措施\n\n### 1. 路由懒加载\n将所有路由组件改为动态import，首屏只加载必要代码。\n\n### 2. 组件库按需引入\n从全量引入Element Plus改为按需引入，减少约60%的组件库体积。\n\n### 3. 打包分析与代码分割\n使用rollup-plugin-visualizer分析打包产物，将大型第三方库单独分包。\n\n### 4. 静态资源优化\n图片压缩、字体子集化、开启gzip。\n\n## 结果\n\n首屏加载时间从5s降到1.5s，打包体积从3MB降到800KB。\n\n## 总结\n\n性能优化不是一次性的工作，需要持续监控和迭代。',
  '前端',
  'Performance,Vite,Optimization',
  9,
  1
);
