package com.study.rpc.core.threadFactory;

import com.study.rpc.core.ThreadLocal.CommonThreadLocal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * 服务端线程池处理请求
 * @author: qirp
 * @since: 2019/12/31 17:22
 **/
public class ThreadPoolHandler implements Runnable{

    private Socket socket;
    private static Map<String,Class<?>> registryMap = CommonThreadLocal.registryThreadLocal.get();

    public ThreadPoolHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String serverName = input.readUTF();
            String methodName = input.readUTF();
            Class<?>[] parameterTypes = (Class<?>[])input.readObject();
            Object[] arguments = (Object[]) input.readObject();
            //获取目标类实例
            Class serverClass = registryMap.get(serverName);
            //拿到具体的执行方法
            Method method = serverClass.getMethod(methodName,parameterTypes);

            //invoke出结果
            Object result = method.invoke(serverClass.newInstance(),arguments);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
