package com.cyz.rpc.fault.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class FixedIntervalRetryStrategy implements RetryStrategy {
    private static final Logger log = LoggerFactory.getLogger(FixedIntervalRetryStrategy.class);

    /**
     * 最大重试次数
     */
    private static final int MAX_RETRY_COUNT = 3;

    /**
     * 重试间隔（毫秒）
     */
    private static final long RETRY_INTERVAL = 1000;

    @Override
    public <T> T doRetry(Callable<T> callable) throws Exception {
        int retryCount = 0;
        while (true) {
            try {
                return callable.call();
            } catch (Exception e) {
                retryCount++;
                if (retryCount > MAX_RETRY_COUNT) {
                    log.error("重试次数已达上限，抛出原始异常", e);
                    throw e;
                }
                log.warn("调用失败，开始第 {} 次重试，间隔 {}ms", retryCount, RETRY_INTERVAL, e);
                Thread.sleep(RETRY_INTERVAL);
            }
        }
    }
}