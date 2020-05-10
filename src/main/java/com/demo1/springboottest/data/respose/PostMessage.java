package com.demo1.springboottest.data.respose;

import java.util.List;

public class PostMessage {
    private boolean succeed;
    private int code;
    private String message;

    public PostMessage(boolean succeed, int code, String message) {
        this.succeed = succeed;
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"succeed\""+":" + succeed +
                ",\"code\"" +":"+ code +
                ", \"message\""+":" + "\""+message+"\"" +
                '}';
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
