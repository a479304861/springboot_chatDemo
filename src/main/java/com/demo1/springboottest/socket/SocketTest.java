package com.demo1.springboottest.socket;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.mapper.Mapper;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketTest {
    private static final int PORT = 8000;
    private ServerSocket serverSocket = null;
    private ExecutorService mExecutorService = null;


    public SocketTest() {
        try {
            this.serverSocket = new ServerSocket(8000, 50);
            this.mExecutorService = Executors.newCachedThreadPool();
            System.out.println("服务器启动");
            Socket client = null;
            while(true) {
                client = this.serverSocket.accept();
                Service service = new Service(client);
                this.mExecutorService.execute(service);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }
}