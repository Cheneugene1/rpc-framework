package com.cyz.rpc.fault.tolerant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class FailBackTolerantStrategy implements TolerantStrategy {
    private static final Logger log = LoggerFactory.getLogger(FailBackTolerantStrategy.class);

    @Override
    public <T> T doTolerant(Callable<T> callable, List<String> serviceList) {
        try {
            return callable.call();
        } catch (Exception e) {
            log.error("失败恢复策略：调用服务失败，记录日志，返回默认值，后台异步重试", e);
            // 这里可以实现后台异步重试的逻辑
            return null;
        }
    }
}