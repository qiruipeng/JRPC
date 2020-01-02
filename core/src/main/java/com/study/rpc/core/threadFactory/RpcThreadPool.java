package com.study.rpc.core.threadFactory;

import java.util.concurrent.*;

/**
 * 自定义rpc线程池
 * @author: qirp
 * @since: 2019/12/31 18:07
 **/
public class RpcThreadPool {
    public Executor getExecutor(int threads,int queues){
        String name = "RpcThreadPool";
        return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>()
                        : (queues < 0 ? new LinkedBlockingQueue<Runnable>()
                        : new LinkedBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }

}
