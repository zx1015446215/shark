package com.zx.shark.likes.service.impl;

import com.zx.shark.likes.model.LikedCountDTO;
import com.zx.shark.likes.model.UserLike;
import com.zx.shark.likes.service.RedisService;
import com.zx.shark.likes.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 点赞，状态为1
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void saveLiked2Redis(long likedUserId, long likedPostId) {
         String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
         redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,1);
    }

    /**
     * 取消点赞，状态为-1
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void unlikeFromRedis(long likedUserId, long likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,-1);
    }

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void deleteLikedFromRedis(long likedUserId, long likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
    }

    /**
     * 该用户的点赞数加1
     * @param likedUserId
     */
    @Override
    public void incrementLikedCount(long likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,1);
    }

    /**
     * 该用户的点赞数减1
     * @param likedUserId
     */
    @Override
    public void decrementLikedCount(long likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,-1);
    }

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            long likedUserId = Long.parseLong(split[0]);
            long likedPostId = Long.parseLong(split[1]);
            Integer status = (Integer)entry.getValue();

            //组成UserLike对象
            UserLike userLike = new UserLike(likedUserId,likedPostId,status);
            list.add(userLike);

            //存在list之后从redis中删除
            deleteLikedFromRedis(likedUserId,likedPostId);
        }
        return list;
    }

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            long likedUserId = (long) entry.getKey();
            Integer count = (Integer) entry.getValue();
            LikedCountDTO likedCountDTO = new LikedCountDTO(likedUserId,count);
            list.add(likedCountDTO);
            //从redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId);
        }
        return list;
    }
}
