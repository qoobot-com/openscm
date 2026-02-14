package com.qoobot.openscm.common.constant;

/**
 * 错误码枚举
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public enum ErrorCode {

    /** 成功 */
    SUCCESS(200, "操作成功"),

    /** 客户端错误 4xx */
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "拒绝访问"),
    NOT_FOUND(404, "请求资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    CONFLICT(409, "资源冲突"),

    /** 服务器错误 5xx */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),

    /** 业务错误 1000-1999 */
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_PASSWORD_ERROR(1002, "用户密码错误"),
    USER_ACCOUNT_DISABLED(1003, "用户账号已禁用"),
    USER_ACCOUNT_LOCKED(1004, "用户账号已锁定"),
    USER_ACCOUNT_EXPIRED(1005, "用户账号已过期"),

    /** 权限错误 2000-2999 */
    PERMISSION_DENIED(2001, "权限不足"),
    ROLE_NOT_FOUND(2002, "角色不存在"),
    MENU_NOT_FOUND(2003, "菜单不存在"),

    /** 数据库错误 3000-3999 */
    DATA_NOT_FOUND(3001, "数据不存在"),
    DATA_EXISTS(3002, "数据已存在"),
    DATA_SAVE_FAILED(3003, "数据保存失败"),
    DATA_DELETE_FAILED(3004, "数据删除失败"),
    DATA_UPDATE_FAILED(3005, "数据更新失败"),

    /** 文件错误 4000-4999 */
    FILE_UPLOAD_FAILED(4001, "文件上传失败"),
    FILE_NOT_FOUND(4002, "文件不存在"),
    FILE_TYPE_NOT_ALLOWED(4003, "文件类型不允许"),
    FILE_SIZE_EXCEEDED(4004, "文件大小超出限制"),

    /** 参数校验错误 5000-5999 */
    PARAM_ERROR(5001, "参数错误"),
    PARAM_REQUIRED(5002, "必填参数缺失"),
    PARAM_INVALID(5003, "参数值无效"),
    PARAM_FORMAT_ERROR(5004, "参数格式错误");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
