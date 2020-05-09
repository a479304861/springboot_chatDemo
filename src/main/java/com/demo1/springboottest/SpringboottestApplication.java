package com.demo1.springboottest;

import com.demo1.springboottest.socket.SocketTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.sql.SQLException;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class SpringboottestApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        SpringApplication.run(SpringboottestApplication.class, args);
        SocketTest socketTest = new SocketTest();


    }
}
