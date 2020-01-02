package com.study.rpc.sever;

import com.study.rpc.core.ThreadLocal.CommonThreadLocal;
import com.study.rpc.core.threadFactory.RpcThreadPool;
import com.study.rpc.core.threadFactory.ThreadPoolHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * 服务端实现
 * 目标就是启动一个一直监听socket的东西，当有socket进来的时候,反序列化，然后通过反射拿到结果
 * @author: qirp
 * @since: 2019/8/14 15:06
 **/
@Slf4j
public class RpcServer {
    //替换成zk等
    private static Map<String,Class<?>> registryMap = CommonThreadLocal.registryThreadLocal.get();
    private int port;
    Executor executor = new RpcThreadPool().getExecutor(100,1000);

    public RpcServer(int port) {
        this.port = port;
    }

    //注册
    public RpcServer registry(Class intface,Class impl){
        if(null == registryMap){
            Map<String,Class<?>> map = new HashMap<>();
            map.put(intface.getName(),impl);
            CommonThreadLocal.registryThreadLocal.set(map);
        }
        return this;
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        log.info("...socketrpc 已经启动...");

        while (true){
            //socket是阻塞的，也就意味着有个队列来存储过来的请求
            //管理客户连接请求的任务是由操作系统来完成的,操作系统把这些连接请求存储在一个先进先出的队列中
            //然后开启一个线程或者从线程池中拿出一个线程执行io请求
            Socket socket = server.accept();
            executor.execute(new ThreadPoolHandler(socket));
        }

    }

}
