package com.zx.shark.mapper;

import com.zx.shark.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CommentMapper {
    List<Comment> selectByCid(Long cid);
    void saveComment(Comment comment);
}