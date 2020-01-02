package com.study.rpc.core.threadFactory;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: qirp
 * @since: 2019/12/31 18:16
 **/
@Slf4j
public class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {
    private final String threadName;

    public AbortPolicyWithReport(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        log.error("触发拒绝策略");
        String msg = String.format("RpcServer["
                        + " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d),"
                        + " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)]",
                threadName, e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
        log.error(msg);
        throw new RejectedExecutionException(msg);
    }
}
