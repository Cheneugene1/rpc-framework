package com.cyz.rpc.fault.tolerant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class FailFastTolerantStrategy implements TolerantStrategy {
    private static final Logger log = LoggerFactory.getLogger(FailFastTolerantStrategy.class);

    @Override
    public <T> T doTolerant(Callable<T> callable, List<String> serviceList) {
        try {
            return callable.call();
        } catch (Exception e) {
            log.error("快速失败策略：调用服务失败，直接抛出异常", e);
            throw new RuntimeException("调用服务失败", e);
        }
    }
}