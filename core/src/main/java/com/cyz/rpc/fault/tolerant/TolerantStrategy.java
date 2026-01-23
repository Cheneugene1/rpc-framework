package com.cyz.rpc.fault.tolerant;

import java.util.List;
import java.util.concurrent.Callable;

public interface TolerantStrategy {

    /**
     * 容错处理
     *
     * @param callable     调用函数
     * @param serviceList  服务列表
     * @param <T>          返回值类型
     * @return            返回值
     */
    <T> T doTolerant(Callable<T> callable, List<String> serviceList);
}