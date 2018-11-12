package com.zx.shark.likes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户点赞表
 */
@Entity
public class UserLike {

    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //被点赞的用户的id
    private long likedUserId;

    //点赞的用户的id
    private long likedPostId;

    //点赞的状态.默认未点赞
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public UserLike() {
    }

    public UserLike(long likedUserId, long likedPostId, Integer status) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(long likedUserId) {
        this.likedUserId = likedUserId;
    }

    public long getLikedPostId() {
        return likedPostId;
    }

    public void setLikedPostId(long likedPostId) {
        this.likedPostId = likedPostId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

