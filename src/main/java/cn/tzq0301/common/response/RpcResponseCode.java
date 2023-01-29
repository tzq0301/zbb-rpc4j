package cn.tzq0301.common.response;

public enum RpcResponseCode {
    SUCCESS(0),
    INVALID_PARAMETER(1);

    private final int code;

    RpcResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
