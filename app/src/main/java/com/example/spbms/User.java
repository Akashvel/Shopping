package com.example.spbms;

public class User {
    String id;
    String customername;
    String customernumber;
    String email;

    public User(){

    }

    public User(String id, String customername, String customernumber, String email) {
        this.id = id;
        this.customername = customername;
        this.customernumber = customernumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getCustomername() {
        return customername;
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public String getEmail() {
        return email;
    }
}
