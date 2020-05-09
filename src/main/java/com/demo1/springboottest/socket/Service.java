package com.demo1.springboottest.socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Service implements Runnable {
    private DataInputStream dis;
    private FileOutputStream fos;
    private Socket socket;
    public static List<Service> mList = new ArrayList();
    public static List<String> UserList = new ArrayList();
    private BufferedReader in = null;
    private PrintWriter printWriter = null;
    private String receiveMsg;
    private String sendMsg;

    public Service(Socket socket) throws IOException {
        this.socket = socket;
        try {
            this.printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            this.printWriter.println("成功连接服务器（服务器发送）");
            System.out.println("来自---》"+socket.getInetAddress() + "成功连接服务器");
            mList.add(this);
            System.out.println(mList);
        } catch (IOException var3) {
            var3.printStackTrace();
            mList.remove(this);
            socket.close();
        }

    }

    public void sendOther(String str) {
        List<Service> list = mList;
        Iterator var4 = list.iterator();
        while(var4.hasNext()) {
            Service other = (Service)var4.next();
            if (other != this) {
                this.sendMsg = "我已接收：" + this.receiveMsg + "（服务器发送）";
                other.printWriter.println(other + this.sendMsg);
            }
        }

    }

    //断开连接
    public void deleteConnect() throws IOException {
        System.out.println("客户端请求断开连接");
        this.printWriter.println("服务端断开连接（服务器发送）");
        mList.remove(this);
        System.out.println(mList);
        this.in.close();
        this.socket.close();
    }

    //登入成功
    public void login() {
        System.out.println("登入成功");
        this.sendMsg = "登入成功";
        this.printWriter.println(this.sendMsg);
    }

    public void run() {
        while(true) {
            try {
                if ((this.receiveMsg = this.in.readLine()) == null) {
                    continue;
                }

                System.out.println("receiveMsg:" + this.receiveMsg);
                if (!this.receiveMsg.subSequence(0, 1).equals("0")) {

                    if (this.receiveMsg.subSequence(0, 1).equals("1")) {
                        this.login();
                        continue;
                    }

                    if (this.receiveMsg.subSequence(0, 1).equals("2")) {
                        this.sendMsg = "我已接收：" + this.receiveMsg + "（服务器发送）";
                        this.printWriter.println(this.sendMsg);
                        continue;
                    }

                    continue;
                }

                this.deleteConnect();
            } catch (Exception var2) {
                var2.printStackTrace();
                mList.remove(this);
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return;
        }
    }
}
