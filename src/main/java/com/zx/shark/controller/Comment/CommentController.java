package com.zx.shark.controller.Comment;

import com.zx.shark.mapper.CommentMapper;
import com.zx.shark.model.Comment;
import com.zx.shark.model.MenuDO;
import com.zx.shark.model.Tree;
import com.zx.shark.service.impl.CommentServiceImpl;
import com.zx.shark.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@RequestMapping("/comment")
@RestController
@CrossOrigin(origins = "*")
public class CommentController {
    private long cid ;
    @Autowired
    CommentServiceImpl commentService;
    @RequestMapping("/list/{cid}")
    public List<Tree<Comment>> getCommentByCid(@PathVariable("cid") long cid){
        this.cid=cid;
        List<Tree<Comment>> commentLists = commentService.findCommentByCid(cid);
        return commentLists;
    }

    @RequestMapping("/saveComment")
    public JSONResult saveComment(@RequestParam Long parent_id,@RequestParam String content){
        try {
            commentService.saveComment(parent_id, content);
        }catch (Exception e){
            System.out.println("错误信息:"+e.toString());
        }
        return JSONResult.ok();
    }
}
