package com.demo1.springboottest.data.receive;

public class SendToAll {
    private int sendId;
    private String content;

    public SendToAll(int sendId, String content) {
        this.sendId = sendId;
        this.content = content;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
