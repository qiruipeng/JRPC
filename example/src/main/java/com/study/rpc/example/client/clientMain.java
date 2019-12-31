package com.study.rpc.example.client;

import com.study.rpc.client.RpcClientProxy;
import com.study.rpc.example.sever.SayIntface;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟客户端程序消费场景
 * @author: qirp
 * @since: 2019/12/31 14:10
 **/
public class clientMain {

    @Test
    public void singleRequest(){
        RpcClientProxy proxy = new RpcClientProxy(SayIntface.class,"127.0.0.1",8080);
        SayIntface sever = (SayIntface) proxy.getClientIntance();
        String result = sever.says("test my first");
        System.out.println(result);
    }

    /**
     * 模拟一万的瞬时并发,就是同时有一万的请求发过去
     * 可以通过countdownlatch，await一直阻塞，直到一万了才去请求
     * @throws InterruptedException
     */
    @Test
    public void mutiRequest() throws InterruptedException {
        //并行度10000
        int parallel = 10000;
        //开始计时
        StopWatch sw = new StopWatch();
        sw.start();

        CountDownLatch signal = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(parallel);

        RpcClientProxy proxy = new RpcClientProxy(SayIntface.class,"127.0.0.1",8080);
        for (int index = 0; index < parallel; index++) {
            ParallelRequestThread requestThread = new ParallelRequestThread(signal,finish,proxy);
            new Thread(requestThread).start();
        }
        //10000个并发线程瞬间发起请求操作
        signal.countDown();
        finish.await();

        sw.stop();

        String tip = String.format("RPC调用总共耗时: [%s] 分钟", sw.getTotalTimeSeconds());
        System.out.println(tip);
    }
}
