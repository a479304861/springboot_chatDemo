package com.demo1.springboottest;

import com.demo1.springboottest.controller.UserController;
import com.demo1.springboottest.data.User;
import com.demo1.springboottest.data.mysql.UserMysql;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.SQLException;


@SpringBootTest(classes = SpringboottestApplication.class)
class SpringboottestApplicationTests {

//    @Test
//    public  void test() throws Exception {
//        Map<String, Object> stringObjectHashMap = new HashMap<>();
//        Mysql mysql = new Mysql();
//        Map<String, Object> name2 = mysql.select("name2");
//        System.out.println(name2);
//        System.out.println(stringObjectHashMap);
//        if (name2.equals(stringObjectHashMap)) {
//            System.out.println("true");
//        }
//        else  System.out.println("false");
//
//    }
    @Test
    public  void test() throws SQLException, IOException, ClassNotFoundException
{
//        UserController userController = new UserController();
//        System.out.println( userController.getMessage(17,5));
//        UserMysql userMysql = new UserMysql();
//        System.out.println(userMysql.selectMessage(17,5));



    }


}

