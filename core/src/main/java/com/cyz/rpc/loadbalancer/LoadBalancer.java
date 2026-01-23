package com.cyz.rpc.loadbalancer;

import java.util.List;
import java.util.Map;

public interface LoadBalancer {

    /**
     * 选择服务
     *
     * @param requestParams 请求参数
     * @param serviceList 服务列表
     * @return 选中的服务名称
     */
    String select(Map<String, Object> requestParams, List<String> serviceList);
}