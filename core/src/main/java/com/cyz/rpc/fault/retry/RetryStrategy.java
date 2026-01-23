package com.cyz.rpc.fault.retry;

import java.util.concurrent.Callable;

public interface RetryStrategy {

    /**
     * 重试
     *
     * @param callable 调用函数
     * @param <T>      返回值类型
     * @return        返回值
     * @throws Exception 异常
     */
    <T> T doRetry(Callable<T> callable) throws Exception;
}