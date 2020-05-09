package com.demo1.springboottest.controller;

import com.demo1.springboottest.data.Params;
import com.demo1.springboottest.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class HelloController {


//    @Autowired
//    private Author author;
//
//    @Autowired
//    private Person person;


    //
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getUser() throws IOException{
            System.out.println("/user");
            Map<String,Object> map = new HashMap<String,Object>();


            //返回值
        map.put("succeed",true);
        User user = new User("zhangsan","123");
        user.setId(1);
        user.setCollectNum(5);
        user.setFansNum(13);
        user.setLikeNum(12);
        List<User> users = new ArrayList<User>();
        users.add(user);
        map.put("data",users);

            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
         }

    //带参数请求
    @RequestMapping(value = "/get/p", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> get(@RequestParam(value = "keyword") String info,@RequestParam(value = "page") int page,@RequestParam(value = "order") int order) throws IOException{
        System.out.println("/get/p");
        System.out.println(info+"   "+page+"   "+order);
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("success",true);
        return new ResponseEntity<Map<String,Object>>(map1,HttpStatus.OK);
    }
}
