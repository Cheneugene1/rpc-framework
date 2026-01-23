package com.cyz.rpc.loadbalancer;

public interface LoadBalancerKeys {
    /**
     * 随机负载均衡器
     */
    String RANDOM = "random";
    /**
     * 轮询负载均衡器
     */
    String ROUND_ROBIN = "roundRobin";
    /**
     * 一致性哈希负载均衡器
     */
    String CONSISTENT_HASH = "consistentHash";
}