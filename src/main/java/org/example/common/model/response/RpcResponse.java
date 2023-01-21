package org.example.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public static RpcResponse<?> invalidParam(final String message) {
        return new RpcResponse<>(RpcResponseCode.INVALID_PARAMETER.getCode(), message, null);
    }
}
