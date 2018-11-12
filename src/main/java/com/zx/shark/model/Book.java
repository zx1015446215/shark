package com.zx.shark.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Book {
    private Long id;
    private String name;  //书名
    private String author;  //作者
    private String type;   //类别
    private String company;  //出版社
    private Timestamp publishtime;    //出版时间
    private int remain;    //剩余数量
    private int total;     //总数
    private Boolean flag = false;    //判断用户是否预约了此书
    public Book(Long id,String name,String type ,String author, String company, Timestamp publishtime, int remain, int total) {

        this.name = name;
        this.type=type;
        this.author = author;
        this.company = company;
        this.publishtime = publishtime;
        this.remain = remain;
        this.total = total;
    }

    public Book(String name,String type, String author, String company, Timestamp publishtime, int total) {
        this.name = name;
        this.type=type;
        this.author = author;
        this.company = company;
        this.publishtime = publishtime;
        this.remain = total;
        this.total = total;
    }

    public Book(String name, String author, String type) {
        this.name = name;
        this.author = author;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Timestamp getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Timestamp publishtime) {
        this.publishtime = publishtime;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
