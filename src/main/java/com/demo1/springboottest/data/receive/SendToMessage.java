package com.demo1.springboottest.data.receive;

public class SendToMessage {
    private int sendId;
    private int receiveId;
    private String content;

    public SendToMessage(int sendId, int receiveId, String content) {
        this.sendId = sendId;
        this.receiveId = receiveId;
        this.content = content;
    }

    public int getSendId() {
        return sendId;
    }

    @Override
    public String toString() {
        return "SendToMessage{" +
                "sendId=" + sendId +
                ", receiveId=" + receiveId +
                ", content='" + content + '\'' +
                '}';
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
