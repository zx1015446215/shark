package com.zx.shark.likes.service.impl;

import com.zx.shark.likes.model.LikedCountDTO;
import com.zx.shark.likes.model.UserLike;
import com.zx.shark.likes.repository.UserLikeRepository;
import com.zx.shark.likes.service.LikedService;
import com.zx.shark.likes.service.RedisService;
import com.zx.shark.model.ContentDO;
import com.zx.shark.service.ContentService;
import com.zx.shark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikedServiceImpl implements LikedService {
    @Autowired
    UserLikeRepository userLikeRepository;
    @Autowired
    RedisService redisService;
    @Autowired
    ContentService contentService;
    /**
     * 保存点赞记录
     * @param userLike
     * @return
     */
    @Override
    public UserLike save(UserLike userLike) {
        return userLikeRepository.save(userLike);
    }

    /**
     * 批量保存或修改
     * @param list
     * @return
     */
    @Override
    public List<UserLike> saveAll(List<UserLike> list) {
        return userLikeRepository.saveAll(list);
    }

    /**
     * 根据被点赞人的id查询点赞列表
     * @param likedUserId 被点赞人的id
     * @param pageable
     * @return
     */
    @Override
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return userLikeRepository.findByLikedUserIdAndStatus(likedUserId,pageable);
    }

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param likedPostId
     * @param pageable
     * @return
     */
    @Override
    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        return userLikeRepository.findByLikedPostIdAndStatus(likedPostId, pageable);
    }

    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    @Override
    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
        return userLikeRepository.findByLikedUserIdAndLikedPostId(likedUserId, likedPostId);
    }

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<UserLike> list = redisService.getLikedDataFromRedis();
        //测试一下会不会重复
        userLikeRepository.saveAll(list);
    }

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        //需要在文章数据表上进行修改，添加点赞数量属性
         for (LikedCountDTO likedCountDTO : list){
             long likedUserId = likedCountDTO.getLikedUserId();
             int count = likedCountDTO.getCount();
             //更新点赞数量
             contentService.update(new ContentDO(likedUserId,count));
         }

    }
}
