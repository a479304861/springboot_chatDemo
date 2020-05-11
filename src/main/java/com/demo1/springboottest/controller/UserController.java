package com.demo1.springboottest.controller;


import com.demo1.springboottest.data.respose.FriendRespose;
import com.demo1.springboottest.data.User;
import com.demo1.springboottest.data.mysql.UserMysql;
import com.demo1.springboottest.data.respose.MessagePost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private static UserMysql mysql;
    //初始化数据库
    public UserController() throws SQLException, ClassNotFoundException {
        mysql = new UserMysql();
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> register(User user) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("/user/register");
        Map<String, Object> result=new HashMap<String, Object>();
        boolean isSucceed = mysql.insert(user.getName(), user.getPassword());
        if (isSucceed=true) {
             result = mysql.select(user.getName());
        }
        //返回值
        List<Object> users = new ArrayList<Object>();
        users.add(result);
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("data",users);
        map1.put("succeed",isSucceed);
        return new ResponseEntity<Map<String,Object>>(map1, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> login(User user) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("/user/login");
//        System.out.println(user);
        Map<String, Object> result=new HashMap<String, Object>();
        String s = mysql.selectPassword(user.getName());
//        System.out.println(s);
        if (s!=null) {
             result = mysql.select(user.getName());
//            System.out.println(result);
        }


        //返回值
        List<Object> users = new ArrayList<Object>();
        users.add(result.get(user.getName()));
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("data",users);
        if (result.equals(new HashMap<String,Object>())) {
            map1.put("succeed",false);
        }
        else if (user.getPassword().equals(s) ) {
            map1.put("succeed",true);
        }
        else {map1.put("succeed",false);}
        System.out.println(map1);
        return new ResponseEntity<Map<String,Object>>(map1, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/getFriend", method = RequestMethod.GET)
        public ResponseEntity<Map<String,Object>> getFriend
            (@RequestParam(value = "id") String  userId) throws IOException, SQLException, ClassNotFoundException {

        System.out.println("/user/getfriend");
        //算出结果   [{key,friendName},{key,friendName}]
        FriendRespose friendRespose = mysql.selectFriend(userId);
        //返回值
        Map<String,Object> result=new HashMap<String, Object>();
        result.put("data",friendRespose.getData());
        result.put("succeed",true);
        System.out.println(result);
        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/getMessage", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getMessage
            (@RequestParam(value = "userId") String  userId
           ,@RequestParam(value = "receiveId") int receiveId) throws IOException, SQLException, ClassNotFoundException {

        System.out.println("/user/getMessage");

        //算出结果   [{key,friendName},{key,friendName}]
        MessagePost messagePost = mysql.selectMessage(userId,receiveId);
        //返回值
        Map<String,Object> result=new HashMap<String, Object>();
        result.put("data",messagePost.getData());
        result.put("succeed",true);
        System.out.println(result);
        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
    }


    @RequestMapping("/sendToUser")
    public ResponseEntity<Map<String,Object>> sendToUser
            (@RequestParam(value = "sendId") String sendId,
             @RequestParam(value = "receiveId") String receiveId,
             @RequestParam(value = "info") String info) {
        System.out.println("/sendToUser");

        Boolean aBoolean = mysql.addMessage(sendId, receiveId, info);

        //返回值
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("success",true);
        return new ResponseEntity<Map<String,Object>>(map1, HttpStatus.OK);
    }

}

