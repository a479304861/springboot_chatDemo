package com.demo1.springboottest.data;

public class Params {
   private String  name;
   private String password;

    public Params(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Params(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Params{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
