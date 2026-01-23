package com.cyz.rpc.fault.retry;

import java.util.concurrent.Callable;

public class NoRetryStrategy implements RetryStrategy {

    @Override
    public <T> T doRetry(Callable<T> callable) throws Exception {
        // 不重试，直接调用
        return callable.call();
    }
}