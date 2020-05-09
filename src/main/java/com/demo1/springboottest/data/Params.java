package com.demo1.springboottest.data;

public class Params {
    private String keyword;
    private int page;
    private int order;

    @Override
    public String toString() {
        return "Params{" +
                "keyword='" + keyword + '\'' +
                ", page=" + page +
                ", order=" + order +
                '}';
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
