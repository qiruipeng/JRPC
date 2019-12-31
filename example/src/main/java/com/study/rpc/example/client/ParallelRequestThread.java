package com.study.rpc.example.client;

import com.study.rpc.client.RpcClientProxy;
import com.study.rpc.example.sever.SayIntface;
import lombok.Data;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * 实现瞬时模拟并发场景
 * @author: qirp
 * @since: 2019/12/31 14:49
 **/
@Data
public class ParallelRequestThread implements Runnable{
    private CountDownLatch single;
    private CountDownLatch finish;
    private RpcClientProxy proxy;

    public ParallelRequestThread(CountDownLatch single, CountDownLatch finish, RpcClientProxy proxy) {
        this.single = single;
        this.finish = finish;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        try {
            //阻塞住，等single为0后继续往后执行
            single.await();
            String result = String.format("你好:%s", UUID.randomUUID());
            SayIntface server = (SayIntface)proxy.getClientIntance();
            server.says(result);
            finish.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
