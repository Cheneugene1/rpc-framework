package com.cyz.rpc.loadbalancer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer implements LoadBalancer {

    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public String select(Map<String, Object> requestParams, List<String> serviceList) {
        if (serviceList == null || serviceList.isEmpty()) {
            return null;
        }
        // 轮询选择一个服务
        int currentIndex = Math.abs(index.getAndIncrement() % serviceList.size());
        return serviceList.get(currentIndex);
    }
}