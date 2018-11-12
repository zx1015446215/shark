package com.zx.shark.likes.controller;

import com.zx.shark.likes.service.RedisService;
import com.zx.shark.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikesController {
    @Autowired
    RedisService redisService;

    @RequestMapping("/like")   //点赞
    public void  likesLike(@RequestParam("likedPostId")long likedPostId){
        //获取点赞人的id
        Long likedUserId = new UserUtils().GetUserMessage();
        redisService.saveLiked2Redis(likedUserId,likedPostId);
        redisService.incrementLikedCount(likedUserId);  //点赞数目加1
    }
    @RequestMapping("/unlike")  //取消点赞
    public void likesUnlike(@RequestParam("likedPostId")long likedPostId){
        //获取点赞人的id
        Long likedUserId = new UserUtils().GetUserMessage();
        redisService.unlikeFromRedis(likedUserId,likedPostId);
        redisService.decrementLikedCount(likedUserId);   //点赞数目减1
    }

}
