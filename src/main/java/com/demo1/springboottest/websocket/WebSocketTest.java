package com.demo1.springboottest.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


@Component
@ServerEndpoint("/websocket/{sessionKey}")
public class WebSocketTest {
    private static final Logger log = Logger.getLogger(WebSocketTest.class.getName());
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static Map<String, WebSocketTest> WebSocketTests = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    @OnOpen
    public void onOpen(Session session, @PathParam("sessionKey") String sessionKey) {

        if (!WebSocketTests.containsKey(sessionKey)) {
            this.session = session;
            WebSocketTests.put(sessionKey, this);
            addOnlineCount();
            log.info("当前websocket连接数：" + onlineCount);
        }
    }


    @OnClose
    public void onClose(@PathParam("sessionKey") String sessionKey) {

        if (WebSocketTests.containsKey(sessionKey)) {
            WebSocketTests.remove(sessionKey);
            subOnlineCount();
            log.info("当前websocket连接数：" + onlineCount);
        }
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" +session+"内容为："+ message);
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.info("websocket发生错误：" + error);
    }


    public static void sendMessage(String sessionKey, String message) throws IOException {

        WebSocketTest webSocket = WebSocketTests.get(sessionKey);

        if (null != webSocket) {

            log.info("websocket发送消息：" + message);

//            //同步发送 发送第二条时，必须等第一条发送完成
//            webSocket.session.getBasicRemote().sendText(message);

            //异步发送
            webSocket.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }
}
