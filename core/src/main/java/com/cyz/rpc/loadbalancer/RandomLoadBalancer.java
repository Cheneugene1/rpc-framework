package com.cyz.rpc.loadbalancer;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer {

    private final Random random = new Random();

    @Override
    public String select(Map<String, Object> requestParams, List<String> serviceList) {
        if (serviceList == null || serviceList.isEmpty()) {
            return null;
        }
        // 随机选择一个服务
        int index = random.nextInt(serviceList.size());
        return serviceList.get(index);
    }
}