package com.zx.shark.likes.repository;

import com.zx.shark.likes.model.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository extends JpaRepository<UserLike,Long> {
    Page<UserLike> findByLikedUserIdAndStatus(String likedUserId, Pageable pageable);

    Page<UserLike> findByLikedPostIdAndStatus(String likedPostId, Pageable pageable);

    UserLike findByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId);
}
