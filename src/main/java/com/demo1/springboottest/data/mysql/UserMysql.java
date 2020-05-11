package com.demo1.springboottest.data.mysql;

import com.demo1.springboottest.data.respose.FriendRespose;
import com.demo1.springboottest.data.User;
import com.demo1.springboottest.data.respose.MessagePost;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserMysql {
    private Connection conn = null;
    private Statement stmt = null;

    public UserMysql() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        long start = System.currentTimeMillis();
        // 建立连接Mysql@localhost
        System.out.println("连接到数据库");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&serverTimezone=UTC",
                "root", "qqwwee123");
        stmt = conn.createStatement();
    }

    //打印对应全部信息
    public Map<String, Object> select(String name) {
        try {

            Map<String, Object> result = new HashMap<String, Object>();
            // 执行SQL语句
            String sql = "select * from user where Name='%s'";
            sql = String.format(sql, name);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
//                System.out.println(rs.getInt(1)+rs.getString(2)+rs.getString(3));
                User user = new User(
                        rs.getString(2),
                        rs.getString(3));
                user.setId(rs.getInt(1));
                user.setLikeNum(rs.getInt(4));
                user.setFansNum(rs.getInt(5));
                user.setCollectNum(rs.getInt(6));
                user.setTransmitNum(rs.getInt(8));
                result.put(rs.getString(2), user);
            }
            return result;
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }


    //注册插入
    public boolean insert(String mname, String mpassword) {
        try {
            if (select(mname).equals(new HashMap<String, Object>())) {
                String sql = "insert into user(Name, Password,LikeNum,collectNUM,fansnum) " + "values('%s','%s',0,0,0)";
                sql = String.format(sql, mname, mpassword);
                // 动态执行SQL语句
                stmt.execute(sql);
                return true;
            } else return false;

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    //找到对应密码
    public String selectPassword(String s) {
        try {
            // 执行SQL语句
            String sql = "select password from user where Name='%s'";
            sql = String.format(sql, s);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    //找到好友
    public FriendRespose selectFriend(String i) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            // 执行SQL语句
            String sql = "SELECT  Userid,name,isonline FROM user WHERE UserId in (select friendid from friend where Userid='%s')";
            sql = String.format(sql, i);
            ResultSet rs = stmt.executeQuery(sql);
            FriendRespose friend = new FriendRespose();
            while (rs.next()) {
                FriendRespose.DataBean dataBean = new FriendRespose.DataBean();
                dataBean.setFriendId(rs.getInt(1));
                dataBean.setName(rs.getString(2));
                dataBean.setOnline(rs.getBoolean(3));
                friend.addData(dataBean);
            }
            return friend;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;

    }
    //
    public MessagePost selectMessage(String sendId, int receiveId) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            // 执行SQL语句
            String sql = "SELECT  * FROM message WHERE SendId ='%s'and ReceiveId='%s'OR(SendId ='%s'and ReceiveId='%s') ORDER BY time";
            sql = String.format(sql, sendId, receiveId, receiveId, sendId);
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            MessagePost messagePost = new MessagePost();
            while (rs.next()) {
                MessagePost.DataBean dataBean = new MessagePost.DataBean();
                dataBean.setSendId(rs.getInt(2));
                dataBean.setReceiveId(rs.getInt(3));
                dataBean.setContent(rs.getString(4));
                messagePost.addData(dataBean);
            }
            return messagePost;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;


    }

    public Boolean addMessage(String sendId, String receiveId, String content) {
        try {

            Calendar cal=Calendar.getInstance();
            //用Calendar类提供的方法获取年、月、日、时、分、秒
            int year  =cal.get(Calendar.YEAR);   //年
            int month =cal.get(Calendar.MONTH)+1;  //月  默认是从0开始  即1月获取到的是0
            int day   =cal.get(Calendar.DAY_OF_MONTH);  //日，即一个月中的第几天
            int hour  =cal.get(Calendar.HOUR_OF_DAY);  //小时
            int minute=cal.get(Calendar.MINUTE);   //分
            int second=cal.get(Calendar.SECOND);  //秒

            //拼接成字符串输出
            String time=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
            System.out.println("当前时间是---->"+time);

            String sql = "insert into message(sendId, receiveId,content,time) " +
                    "values(%s,%s,\"%s\",\"%s\")";
            sql = String.format(sql,sendId,receiveId, content,time);
            System.out.println(sql);
            // 动态执行SQL语句
            stmt.execute(sql);
            return true;

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
