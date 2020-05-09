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
        private boolean isOnline;

        public boolean isOnline() {
            return isOnline;
        }

        public void setOnline(boolean online) {
            isOnline = online;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "friendId=" + friendId +
                    ", name='" + name + '\'' +
                    '}';
        }

        private String name;
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
