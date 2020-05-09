package com.demo1.springboottest.data.respose;


import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MessagePost {
    private boolean succeed;
    private List<DataBean> data;

    public MessagePost() {
        this.succeed = false;
        this.data = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "MessageRespose{" +
                "succeed=" + succeed +
                ", data=" + data +
                '}';
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void addData(DataBean dataBean) {
        data.add(dataBean);
    }

    public static class DataBean{
        private int sendId;
        private int receiveId;

        @Override
        public String toString() {
            return "DataBean{" +
                    "sendId=" + sendId +
                    ", receiveId=" + receiveId +
                    ", content='" + content + '\'' +
                    '}';
        }

        private String content;


        public int getSendId() {
            return sendId;
        }

        public void setSendId(int sendId) {
            this.sendId = sendId;
        }

        public int getReceiveId() {
            return receiveId;
        }

        public void setReceiveId(int receiveId) {
            this.receiveId = receiveId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }



    }
}
