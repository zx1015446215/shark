package com.zx.shark.likes.model;

/**
 * 存储被点赞的id和数量
 */
public class LikedCountDTO {
    private long likedUserId;   //key
    private int count;    //点赞数量

    public LikedCountDTO(long likedUserId, int count) {
        this.likedUserId = likedUserId;
        this.count = count;
    }

    public long getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(long likedUserId) {
        this.likedUserId = likedUserId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
