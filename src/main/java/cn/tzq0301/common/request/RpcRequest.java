package cn.tzq0301.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest<T> {
    private String serviceName;
    private T data;
}
