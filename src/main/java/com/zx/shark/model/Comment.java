package com.zx.shark.model;

import java.sql.Timestamp;

public class Comment {
    private Long id;
    private Long cid;
    private Long user_id;
    private Long parent_id;
    private String content;
    private Timestamp date;

    public Comment(Long cid, Long user_id, Long parent_id, String content, Timestamp date) {
        this.cid = cid;
        this.user_id = user_id;
        this.parent_id = parent_id;
        this.content = content;
        this.date = date;
    }

    public Comment(Long id, Long cid, Long user_id, Long parent_id , String content, Timestamp date) {
        this.id = id;
        this.cid = cid;
        this.user_id = user_id;
        this.parent_id = parent_id;
        this.content = content;
        this.date = date;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
