package com.bosszhipin.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 通用
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "服务器内部错误"),

    // 认证 (1xxx)
    USERNAME_EXISTS(1001, "用户名已存在"),
    USER_NOT_FOUND(1002, "用户不存在"),
    PASSWORD_ERROR(1003, "密码错误"),
    TOKEN_EXPIRED(1004, "Token已过期"),
    TOKEN_INVALID(1005, "Token无效"),
    ACCOUNT_DISABLED(1006, "账号已被禁用"),

    // 业务 (2xxx)
    PROFILE_NOT_FOUND(2001, "个人资料不存在"),
    EDUCATION_NOT_FOUND(2002, "教育经历不存在"),
    WORK_EXP_NOT_FOUND(2003, "工作经历不存在"),
    JOB_NOT_FOUND(2004, "职位不存在"),
    ALREADY_FAVORITED(2005, "已收藏该职位"),
    FAVORITE_NOT_FOUND(2006, "收藏记录不存在"),
    ALREADY_APPLIED(2007, "已投递该职位"),
    APPLICATION_NOT_FOUND(2008, "投递记录不存在"),

    // 文件 (3xxx)
    FILE_UPLOAD_FAILED(3001, "文件上传失败"),
    FILE_SIZE_EXCEEDED(3002, "文件大小超出限制"),
    FILE_TYPE_UNSUPPORTED(3003, "不支持的文件类型");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
