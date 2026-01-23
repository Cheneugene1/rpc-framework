package com.cyz.rpc.loadbalancer;

import com.cyz.rpc.spi.SpiLoader;

public class LoadBalancerFactory {

    static {
        // 加载所有负载均衡器
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取负载均衡器实例
     *
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        if (key == null || key.isEmpty()) {
            return DEFAULT_LOAD_BALANCER;
        }
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}