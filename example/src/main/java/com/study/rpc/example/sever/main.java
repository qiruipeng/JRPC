package com.study.rpc.example.sever;

import com.study.rpc.example.sever.impl.SayService;
import com.study.rpc.sever.RpcServer;

import java.io.IOException;

/**服务端启动程序
 * @author: qirp
 * @since: 2019/12/31 14:09
 **/
public class main {
    public static void main(String[] args) throws IOException {
        new RpcServer(8080).registry(SayIntface.class, SayService.class).start();
    }
}
