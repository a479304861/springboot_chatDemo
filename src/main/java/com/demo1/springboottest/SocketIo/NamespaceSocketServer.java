package com.demo1.springboottest.SocketIo;



import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.demo1.springboottest.data.Params;
import com.demo1.springboottest.data.mysql.UserMysql;
import com.demo1.springboottest.data.receive.SendToAll;
import com.demo1.springboottest.data.receive.SendToMessage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class NamespaceSocketServer {





    public static void main() throws SQLException, ClassNotFoundException {
        /*
         * 创建Socket，并设置监听端口
         */
        UserMysql mysql = new UserMysql();
        Configuration config = new Configuration();
        config.setPort(9091);
        final SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        SocketIOServer server = new SocketIOServer(config);

        Map<String, SocketIOClient> nameToClient=new HashMap<>();
        Map<Object,String > clientToName=new HashMap<>();

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                // 判断是否有客户端连接
                if (client != null) {
                    System.out.println(client.getSessionId() + " has connected.");
                    client.joinRoom("broadcast");
//                    client.joinRoom(client.getHandshakeData().getSingleUrlParam("broadcast"));
//                    String room = client.getHandshakeData().getSingleUrlParam("broadcast");
//                    server.getRoomOperations(room).sendEvent("newMessage", "result");
                } else {
                    System.out.println(" !!!!has connected.");
                }
            }
        });
        /*
         * 添加监听事件，监听客户端的事件
         * 1.第一个参数eventName需要与客户端的事件要一致
         * 2.第二个参数eventClase是传输的数据类型
         * 3.第三个参数listener是用于接收客户端传的数据，数据类型需要与eventClass一致
         */


//        server.addEventListener("login", Params.class, new DataListener<Params>() {
//            @Override
//            public void onData(SocketIOClient client, Params data, AckRequest ackRequest) {
//                System.out.println("接收到客户端login消息：Name = " + data.getName());
//                // check is ack requested by client, but it's not required check
////                nameToClient.put(data.getName(),client);
//                // 向客户端发送消息
////                Map<String, Object> result = new HashMap<>();
////                result.put("有用户，登录成功，sessionId=" , data.getName());
//                // 第一个参数必须与eventName一致，第二个参数data必须与eventClass一致
//                String room = client.getHandshakeData().getSingleUrlParam("broadcast");
//                server.getRoomOperations(room).sendEvent("login", "result");
//            }
//        });
//        server.addEventListener("sendTo", SendToMessage.class, new DataListener<SendToMessage>() {
//            @Override
//            public void onData(SocketIOClient client, SendToMessage data, AckRequest ackRequest) {
//                System.out.println("接收到客户端login消息：code = " + data.toString());
//                // check is ack requested by client, but it's not required check
//
//                // 向客户端发送消息
//                Map<String, Object> result = new HashMap<>();
//                result.put("code",1);
//                nameToClient.get(data.getReceiveId()).joinRoom("temp");
//                client.joinRoom("temp");
//                // 第一个参数必须与eventName一致，第二个参数data必须与eventClass一致
//                String room = client.getHandshakeData().getSingleUrlParam("temp");
//
//                server.getRoomOperations(room).sendEvent("newMessage", result);
//                client.leaveRoom("temp");
//                nameToClient.get(data.getReceiveId()).leaveRoom("temp");
//            }
//        });
        //上线广播   code-->3
        server.addEventListener("newMessage", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String s, AckRequest ackRequest) throws Exception {
                System.out.println("socket.io/newMessage");
                System.out.println(s);


                Map<String, Object> result = new HashMap<>();
                result.put("code", 3);
                server.getRoomOperations("broadcast").sendEvent("reNewMessage", result);
            }
        });

        //登入
        server.addEventListener("login", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String s, AckRequest ackRequest) throws Exception {
                System.out.println("socket.io/login");
                System.out.println("账号：" + s +"ssid:"+client.getSessionId()+"已上线");
                //上线通知code --》1

                nameToClient.put(s,client);
                clientToName.put(String.valueOf(client.getSessionId()),s);
//                nameToClient.put(s,client);
                Map<String , Object> map = new HashMap<>();
                map.put("code",1);
                server.getRoomOperations("broadcast").sendEvent("reLogin",map);
            }
        });




    //心跳检测
        server.addEventListener("answer",String.class,(client,data,ackRequest)->{
//            System.out.println("socket.io/answer");
//            System.out.println("收到answer->" + data);
        });
        //断开连接
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("有用户断开连接");
                String s = clientToName.get(String.valueOf(client.getSessionId()));
                System.out.println(s+":用户断开连接");
                try {
                    mysql.downline(s);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                nameToClient.remove(s);
                clientToName.remove(String.valueOf(client.getSessionId()));

                //下线通知code --》1
                Map<String , Object> map = new HashMap<>();
                map.put("code",1);
                server.getRoomOperations("broadcast").sendEvent("reLogin",map);
            }
        });
        server.start();
        System.out.println("SocketIO服务器启动");

        //心跳发出
        while (true){
            try {
                Thread.sleep(1500);
                //广播消息 code-->100
                Map<String , Object> map = new HashMap<>();
                map.put("code","100");
                server.getBroadcastOperations().sendEvent("broadcast",map);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}

//添加系统外监听
//    private void SocetIOPrivilegeListener() {
//        server.addEventListener("privilege",String.class, new DataListener<String>() {
//            @Override
//            public void onData(SocketIOClient client, String data, AckRequest arg2)
//                    throws Exception {
//                System.out.println(client.getRemoteAddress()+"接收到的系统外信息为：" + data+"\r\n");
//                int differentcase=jsonUtil.getCase(data);
//                dateProcessing.ReceivedDate(data,differentcase,client);
//            }
//        });
//    }


