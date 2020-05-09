package com.demo1.springboottest.controller;

import com.demo1.springboottest.websocket.SpringWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/websocket", method = {RequestMethod.POST, RequestMethod.GET})/*GET请求开放用于测试，最好只允许POST请求*/
public class WebSocketController {
    @Autowired
    SpringWebSocketHandler springWebSocketHandler;
    /**
     * 登录将username放入session中，然后在拦截器HandshakeInterceptor中取出
     */
    @ResponseBody
    @RequestMapping("/login")
    public ResponseEntity<Map<String,Object>>  login(HttpServletRequest request, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        System.out.println("登录：" + username + "：" + password);
        HttpSession session = request.getSession();

        // 返回值
        Map<String,Object> map1 = new HashMap<String,Object>();
        if (null != session) {
            session.setAttribute("SESSION_USERNAME", username);
            map1.put("success",true);
        } else {
            map1.put("success",false);
        }
        return new ResponseEntity<Map<String,Object>>(map1, HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping("/hello")
    public ResponseEntity<Map<String,Object>> hello(){
        System.out.println("/hello");
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("success",true);
        return new ResponseEntity<Map<String,Object>>(map1, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping("/sendToUser")
    public String send(@RequestParam(value = "username") String username, @RequestParam(value = "info") String info) {
        System.out.println("/sendToUser");
        springWebSocketHandler.sendMessageToUser(username, new TextMessage(info));
        System.out.println("发送至：" + username);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/broadcast")
    public ResponseEntity<Map<String,Object>> broadcast(@RequestParam(value = "info") String info) {
        springWebSocketHandler.sendMessageToUsers(new TextMessage("广播消息：" + info));
        System.out.println("广播成功");

        //返回值
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("success",true);
        return new ResponseEntity<Map<String,Object>>(map1, HttpStatus.OK);
    }

}
