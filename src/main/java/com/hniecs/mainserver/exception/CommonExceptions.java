package com.hniecs.mainserver.exception;

/**
 * @desc    常用异常 CommonExceptions.java
 * @author  yijie
 * @date    2020-11-11 16:55
 * @logs[0] 2020-11-11 16:55 yijie 创建了CommonExceptions.java文件
 */
public enum CommonExceptions {
    BAD_REQUEST(400, "参数校验错误"),
    UNAUTHORIZED(401, "未经授权"),
    PAYMENT_REQUIRED(402, "需要付款"),
    FORBIDDEN(403, "禁止的"),
    NOT_FOUND(404, "未找到"),
    METHOD_NOT_ALLOWED(405, "不允许的方法"),
    NOT_ACCEPTABLE(406, "不能接受的"),
    PROXY_AUTHENTICATION_REQUIRED(407, "需要代理身份验证"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "冲突"),
    GONE(410, "走了"),
    LENGTH_REQUIRED(411, "所需长度"),
    PRECONDITION_FAILED(412, "前提条件失败"),
    PAYLOAD_TOO_LARGE(413, "有效负载过大"),
    URI_TOO_LONG(414, "URI太长"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    RANGE_NOT_SATISFIABLE(416, "范围无法满足"),
    EXPECTATION_FAILED(417, "预期失败"),
    I_M_A_TEAPOT(418, "茶壶冲泡咖啡"),
    MISDIRECTED_REQUEST(421, "错误请求"),
    UNPROCESSABLE_ENTITY(422, "不可处理实体"),
    LOCKED(423, "已锁定"),
    FAILED_DEPENDENCY(424, "依赖失败"),
    TOO_EARLY(425, "太早了"),
    UPGRADE_REQUIRED(426, "需要升级"),
    PRECONDITION_REQUIRED(428, "前提条件"),
    TOO_MANY_REQUESTS(429, "请求太多"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "请求标头字段太大"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "由于法律原因而无法使用"),

    INTERNAL_SERVER_ERROR(500, "内部服务器错误"),
    NOT_IMPLEMENTED(501, "未实现"),
    BAD_GATEWAY(502, "错误的网关"),
    SERVICE_UNAVAILABLE(503, "暂停服务"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "不支持HTTP版本"),
    VARIANT_ALSO_NEGOTIATES(506, "服务器有一个内部配置错误"),
    INSUFFICIENT_STORAGE(507, "存储空间不足"),
    LOOP_DETECTED(508, "检测到死循环"),
    NOT_EXTENDED(510, "需要对请求进一步扩展"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "需要网络验证"),

    FILE_ERROR(600,"未知文件错误"),
    BAD_FILE_ADDRESS_ERROR(601,"错误的文件路径"),
    FILE_BIG(602,"文件过大"),
    BAD_FILE_TYPE(603,"文件类型错误"),
    NOT_FILE(604,"未选择文件"),
    FILE_UPLOAD_FAIL(605,"文件上传错误");



    public RuntimeException exception;

    CommonExceptions(Integer code, String message) {
        this.exception = new RuntimeException("[" + code + "] {" + message + "}");
    }
}
