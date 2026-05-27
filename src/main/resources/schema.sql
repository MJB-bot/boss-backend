-- ============================================
-- Boss直聘风格招聘平台 - 数据库初始化脚本
-- MySQL 8.0+
-- ============================================

CREATE DATABASE IF NOT EXISTS boss_zhipin
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE boss_zhipin;

-- ============================================
-- 1. 用户表
-- ============================================
CREATE TABLE IF NOT EXISTS `user` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '主键',
    `username`      VARCHAR(50)     NOT NULL                 COMMENT '用户名（登录账号）',
    `password`      VARCHAR(255)    NOT NULL                 COMMENT '密码（BCrypt加密）',
    `phone`         VARCHAR(20)     DEFAULT NULL             COMMENT '手机号',
    `email`         VARCHAR(100)    DEFAULT NULL             COMMENT '邮箱',
    `role`          VARCHAR(20)     NOT NULL DEFAULT 'JOB_SEEKER' COMMENT '角色',
    `status`        TINYINT         NOT NULL DEFAULT 1       COMMENT '状态: 1=正常, 0=禁用',
    `created_at`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_user_status` (`status`),
    KEY `idx_user_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================================
-- 2. 个人资料表（1:1 user）
-- ============================================
CREATE TABLE IF NOT EXISTS `profile` (
    `id`             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`        BIGINT       NOT NULL                COMMENT '用户ID',
    `real_name`      VARCHAR(50)  DEFAULT NULL            COMMENT '真实姓名',
    `avatar_url`     VARCHAR(255) DEFAULT NULL            COMMENT '头像URL',
    `gender`         TINYINT      NOT NULL DEFAULT 0      COMMENT '性别: 0=未知, 1=男, 2=女',
    `birth_date`     DATE         DEFAULT NULL            COMMENT '出生日期',
    `city`           VARCHAR(50)  DEFAULT NULL            COMMENT '所在城市',
    `job_status`     VARCHAR(30)  DEFAULT NULL            COMMENT '求职状态',
    `summary`        TEXT         DEFAULT NULL            COMMENT '个人简介',
    `phone_visible`  TINYINT      NOT NULL DEFAULT 1      COMMENT '手机号是否可见: 1=是, 0=否',
    `email_visible`  TINYINT      NOT NULL DEFAULT 1      COMMENT '邮箱是否可见: 1=是, 0=否',
    `created_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_profile_user_id` (`user_id`),
    CONSTRAINT `fk_profile_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='个人资料表';

-- ============================================
-- 3. 教育经历表（N:1 user）
-- ============================================
CREATE TABLE IF NOT EXISTS `education` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     BIGINT       NOT NULL                COMMENT '用户ID',
    `school_name` VARCHAR(100) NOT NULL                COMMENT '学校名称',
    `degree`      VARCHAR(50)  DEFAULT NULL            COMMENT '学历',
    `major`       VARCHAR(100) DEFAULT NULL            COMMENT '专业',
    `start_date`  DATE         DEFAULT NULL            COMMENT '开始日期',
    `end_date`    DATE         DEFAULT NULL            COMMENT '结束日期',
    `description` TEXT         DEFAULT NULL            COMMENT '在校描述',
    `sort_order`  INT          NOT NULL DEFAULT 0      COMMENT '排序',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_education_user_id` (`user_id`),
    CONSTRAINT `fk_education_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教育经历表';

-- ============================================
-- 4. 工作经历表（N:1 user）
-- ============================================
CREATE TABLE IF NOT EXISTS `work_experience` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`      BIGINT       NOT NULL                COMMENT '用户ID',
    `company_name` VARCHAR(100) NOT NULL                COMMENT '公司名称',
    `position`     VARCHAR(100) DEFAULT NULL            COMMENT '职位',
    `start_date`   DATE         DEFAULT NULL            COMMENT '开始日期',
    `end_date`     DATE         DEFAULT NULL            COMMENT '结束日期',
    `description`  TEXT         DEFAULT NULL            COMMENT '工作描述',
    `sort_order`   INT          NOT NULL DEFAULT 0      COMMENT '排序',
    `created_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_work_exp_user_id` (`user_id`),
    CONSTRAINT `fk_work_exp_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作经历表';

-- ============================================
-- 5. 职位表
-- ============================================
CREATE TABLE IF NOT EXISTS `job` (
    `id`                  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`               VARCHAR(200) NOT NULL                COMMENT '职位名称',
    `company_name`        VARCHAR(200) NOT NULL                COMMENT '公司名称',
    `company_logo`        VARCHAR(255) DEFAULT NULL            COMMENT '公司logo',
    `salary_min`          INT          DEFAULT NULL            COMMENT '最低薪资',
    `salary_max`          INT          DEFAULT NULL            COMMENT '最高薪资',
    `salary_type`         VARCHAR(20)  DEFAULT NULL            COMMENT '薪资类型: MONTHLY/YEARLY',
    `city`                VARCHAR(50)  DEFAULT NULL            COMMENT '城市',
    `district`            VARCHAR(100) DEFAULT NULL            COMMENT '区域',
    `address`             VARCHAR(255) DEFAULT NULL            COMMENT '详细地址',
    `experience_required` VARCHAR(50)  DEFAULT NULL            COMMENT '经验要求',
    `education_required`  VARCHAR(50)  DEFAULT NULL            COMMENT '学历要求',
    `job_type`            VARCHAR(30)  DEFAULT NULL            COMMENT '工作类型: FULL_TIME/PART_TIME',
    `tags`                VARCHAR(500) DEFAULT NULL            COMMENT '标签(JSON数组)',
    `description`         TEXT         DEFAULT NULL            COMMENT '职位描述',
    `headcount`           INT          NOT NULL DEFAULT 1      COMMENT '招聘人数',
    `view_count`          INT          NOT NULL DEFAULT 0      COMMENT '浏览次数',
    `is_hot`              TINYINT      NOT NULL DEFAULT 0      COMMENT '热门: 1=是, 0=否',
    `is_active`           TINYINT      NOT NULL DEFAULT 1      COMMENT '上架: 1=是, 0=否',
    `created_at`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_job_city` (`city`),
    KEY `idx_job_is_hot` (`is_hot`),
    KEY `idx_job_is_active` (`is_active`),
    KEY `idx_job_created_at` (`created_at`),
    FULLTEXT KEY `ft_job_title_company` (`title`, `company_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='职位表';

-- ============================================
-- 6. 收藏表（N:N user <-> job）
-- ============================================
CREATE TABLE IF NOT EXISTS `favorite` (
    `id`         BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`    BIGINT   NOT NULL                COMMENT '用户ID',
    `job_id`     BIGINT   NOT NULL                COMMENT '职位ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_job` (`user_id`, `job_id`),
    KEY `idx_favorite_user_id` (`user_id`),
    KEY `idx_favorite_job_id` (`job_id`),
    CONSTRAINT `fk_favorite_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_favorite_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';

-- ============================================
-- 7. 投递记录表（N:N user <-> job）
-- ============================================
CREATE TABLE IF NOT EXISTS `application` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       BIGINT       NOT NULL                COMMENT '用户ID',
    `job_id`        BIGINT       NOT NULL                COMMENT '职位ID',
    `resume_status` VARCHAR(30)  NOT NULL DEFAULT 'PENDING' COMMENT '状态: PENDING/VIEWED/INTERVIEW/OFFER/REJECTED',
    `cover_letter`  TEXT         DEFAULT NULL            COMMENT '求职信',
    `created_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '投递时间',
    `updated_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_app_user_job` (`user_id`, `job_id`),
    KEY `idx_application_user_id` (`user_id`),
    KEY `idx_application_job_id` (`job_id`),
    KEY `idx_application_status` (`resume_status`),
    CONSTRAINT `fk_application_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_application_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='投递记录表';
