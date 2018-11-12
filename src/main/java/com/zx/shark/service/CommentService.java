package com.zx.shark.service;

import com.zx.shark.model.Comment;
import com.zx.shark.model.Tree;

import java.util.List;

public interface CommentService {
    List<Tree<Comment>>  findCommentByCid(Long cid);
    void saveComment(Long parent_id,String content);
}
