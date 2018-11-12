package com.zx.shark.likes.config;

import com.zx.shark.likes.service.LikedService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;

@Configuration
public class LikeTask extends QuartzJobBean{

    @Autowired
    LikedService likedService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时任务开始");

        //将redis里面的信息同步到数据库中
        likedService.transLikedFromRedis2DB();;
        likedService.transLikedCountFromRedis2DB();
    }
}
