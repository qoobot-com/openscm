package com.qoobot.openscm.common.constant;

/**
 * 通用常量
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public class CommonConstants {

    private CommonConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /** UTF-8 编码 */
    public static final String UTF8 = "UTF-8";

    /** 成功标识 */
    public static final String SUCCESS = "success";

    /** 失败标识 */
    public static final String FAIL = "fail";

    /** 默认当前用户ID */
    public static final Long DEFAULT_USER_ID = -1L;

    /** 默认部门ID */
    public static final Long DEFAULT_DEPT_ID = 0L;

    /** 管理员角色标识 */
    public static final String ADMIN_ROLE = "admin";

    /** 分页默认页码 */
    public static final int DEFAULT_PAGE_NUM = 1;

    /** 分页默认每页条数 */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /** 分页最大每页条数 */
    public static final int MAX_PAGE_SIZE = 100;

    /** 日期时间格式 */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 日期格式 */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /** 时间格式 */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Redis 缓存前缀 */
    public static final String CACHE_PREFIX = "openscm:";

    /** Token 前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** Token 头部 */
    public static final String TOKEN_HEADER = "Authorization";

    /** 缓存键分隔符 */
    public static final String CACHE_KEY_SEPARATOR = ":";

}
