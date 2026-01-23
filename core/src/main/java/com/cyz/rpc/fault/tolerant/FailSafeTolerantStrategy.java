package com.cyz.rpc.fault.tolerant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class FailSafeTolerantStrategy implements TolerantStrategy {
    private static final Logger log = LoggerFactory.getLogger(FailSafeTolerantStrategy.class);

    @Override
    public <T> T doTolerant(Callable<T> callable, List<String> serviceList) {
        try {
            return callable.call();
        } catch (Exception e) {
            log.warn("失败安全策略：调用服务失败，忽略异常，返回默认值", e);
            return null;
        }
    }
}