package com.demo1.springboottest.data.respose;

import java.util.ArrayList;
import java.util.List;

public class FriendRespose {
    private boolean succeed;
    private List<DataBean> data;

    public FriendRespose() {
        this.succeed = false;
        this.data = new ArrayList<DataBean>();
    }

    @Override
    public String toString() {
        return "FriendRespose{" +
                "succeed=" + succeed +
                ", data=" + data +
                '}';
    }

    public static class DataBean{
        private int friendId;
        private String name;
        private String isOnline;
        private String lastMessage;
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "friendId=" + friendId +
                    ", isOnline='" + isOnline + '\'' +
                    ", lastMessage='" + lastMessage + '\'' +
                    ", time='" + time + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getFriendId() {
            return friendId;
        }
        public void setFriendId(int friendId) {
            this.friendId = friendId;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    public boolean getSucceed() {
        return succeed;
    }
    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }
    public List<DataBean> getData() {
        return data;
    }
    public void addData(DataBean dataBean) {
        data.add(dataBean);
    }
}
