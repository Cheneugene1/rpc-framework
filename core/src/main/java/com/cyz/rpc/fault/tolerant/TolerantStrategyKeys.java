package com.cyz.rpc.fault.tolerant;

public interface TolerantStrategyKeys {
    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";
    /**
     * 失败转移
     */
    String FAIL_OVER = "failOver";
    /**
     * 失败安全
     */
    String FAIL_SAFE = "failSafe";
    /**
     * 失败恢复
     */
    String FAIL_BACK = "failBack";
}