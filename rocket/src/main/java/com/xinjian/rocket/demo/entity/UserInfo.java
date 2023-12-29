package com.xinjian.rocket.demo.entity;

public class UserInfo {
    private  String ip;
    private  String gender;
    private  String age;
    private  String username;
    private  String location;
    private  String accountId;

    public UserInfo() {
    }

    public UserInfo(String ip, String gender, String age, String username, String location, String accountId) {
        this.ip = ip;
        this.gender = gender;
        this.age = age;
        this.username = username;
        this.location = location;
        this.accountId = accountId;
    }

    public UserInfo(String ip, String gender, String age, String username, String location) {
        this.ip = ip;
        this.gender = gender;
        this.age = age;
        this.username = username;
        this.location = location;

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "ip='" + ip + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", username='" + username + '\'' +
                ", location='" + location + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
