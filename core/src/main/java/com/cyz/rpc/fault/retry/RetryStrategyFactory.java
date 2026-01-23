package com.cyz.rpc.fault.retry;

import com.cyz.rpc.spi.SpiLoader;

public class RetryStrategyFactory {

    static {
        // 加载所有重试策略
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试策略
     */
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    /**
     * 获取重试策略实例
     *
     * @param key
     * @return
     */
    public static RetryStrategy getInstance(String key) {
        if (key == null || key.isEmpty()) {
            return DEFAULT_RETRY_STRATEGY;
        }
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}