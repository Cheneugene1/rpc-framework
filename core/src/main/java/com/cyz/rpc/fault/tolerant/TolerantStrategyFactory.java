package com.cyz.rpc.fault.tolerant;

import com.cyz.rpc.spi.SpiLoader;

public class TolerantStrategyFactory {

    static {
        // 加载所有容错策略
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取容错策略实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        if (key == null || key.isEmpty()) {
            return DEFAULT_TOLERANT_STRATEGY;
        }
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}