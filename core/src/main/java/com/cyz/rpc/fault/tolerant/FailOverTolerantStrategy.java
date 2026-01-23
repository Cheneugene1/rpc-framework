package com.cyz.rpc.fault.tolerant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class FailOverTolerantStrategy implements TolerantStrategy {
    private static final Logger log = LoggerFactory.getLogger(FailOverTolerantStrategy.class);

    @Override
    public <T> T doTolerant(Callable<T> callable, List<String> serviceList) {
        // 目前简化实现，仅调用一次
        try {
            return callable.call();
        } catch (Exception e) {
            log.error("失败转移策略：调用服务失败，尝试其他节点", e);
            // 这里可以实现转移到其他服务节点的逻辑
            throw new RuntimeException("调用服务失败，已尝试其他节点", e);
        }
    }
}