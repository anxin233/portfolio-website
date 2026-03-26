-- ============================================
-- 管理员用户表
-- ============================================

USE `portfolio_db`;

CREATE TABLE IF NOT EXISTS `admin_user` (
  `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username`      VARCHAR(50)  NOT NULL UNIQUE         COMMENT '用户名',
  `password`      VARCHAR(255) NOT NULL                COMMENT '密码（SHA-256哈希）',
  `nickname`      VARCHAR(50)  DEFAULT NULL             COMMENT '昵称',
  `last_login_at` DATETIME     DEFAULT NULL             COMMENT '最后登录时间',
  `created_at`    DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员用户表';

-- 默认管理员账号: admin / admin123
-- 密码是 SHA-256("admin123") 的 Base64 编码
INSERT INTO `admin_user` (`username`, `password`, `nickname`)
SELECT 'admin', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', '管理员'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `admin_user` WHERE `username` = 'admin');
