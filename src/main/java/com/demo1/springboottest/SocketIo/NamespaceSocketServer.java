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

    private static Map<String, SocketIOClient> nameToClient;
    private static UserMysql mysql;

    public NamespaceSocketServer() throws SQLException, ClassNotFoundException {
        nameToClient = new HashMap<>();
        mysql = new UserMysql();
    }

    public static void main() {
        /*
         * 创建Socket，并设置监听端口
         */
        Configuration config = new Configuration();
        config.setPort(9091);
        config.setHostname("localhost");
        final SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        SocketIOServer server = new SocketIOServer(config);


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
        server.addEventListener("broadcast", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String s, AckRequest ackRequest) throws Exception {
                Map<String, Object> result = new HashMap<>();
                System.out.println(s.toString());
                result.put("code", 100);
                String room = client.getHandshakeData().getSingleUrlParam("broadcast");
                server.getRoomOperations(room).sendEvent("newMessage", result);
            }
        });

        server.addEventListener("sub", Params.class, (client, data, ackRequest) -> {

            System.out.println("server: " + "：客户端：订阅成功，订阅信息為->" + data.getName());
            //sub事件成功反e
            Map<String , Object> map = new HashMap<>();
            map.put("name","nihao");
            server.getRoomOperations("broadcast").sendEvent("sub",map);
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println("有用户断开连接");
            }
        });
        server.start();
        System.out.println("SocketIO服务器启动");
        while (true){
            try {
                Thread.sleep(1500);
                //广播消息
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


