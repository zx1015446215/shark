package com.zx.shark.controller.blog;

import com.zx.shark.model.ContentDO;
import com.zx.shark.service.ContentService;
import com.zx.shark.utils.DateUtils;
import com.zx.shark.utils.PageUtils;
import com.zx.shark.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/blog")
@CrossOrigin(origins = "*")
@Controller
public class BlogController {
    @Autowired
    ContentService contentService;

    //首页
    @GetMapping()
    String blog(){
        return "index/index";
    }

        //获取首页文章的集合
    @ResponseBody
    @GetMapping("/open/list")
    public PageUtils openList(@RequestParam Map<String,Object> params){
        Query query = new Query(params);
        List<ContentDO> ContentList = contentService.list(query);
        int total = contentService.count(query);
        PageUtils pageUtils = new PageUtils(ContentList,total);
        return pageUtils;
    }

    @GetMapping("/open/post/{cid}")
    @ResponseBody
    ContentDO post(@PathVariable("cid") long cid){
        ContentDO contentDO = contentService.get(cid);
//        modelAndView.addObject("bContent",contentDO);
//        modelAndView.addObject("gtmModified",Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
//        modelAndView.setViewName("index/article");
        return contentDO ;
    }

    @GetMapping("/open/communication")
        ModelAndView communication(){
        ModelAndView modelAndView = new ModelAndView("communication");
        return modelAndView;
        }
}
