package com.zx.shark.doFile.controller;


import com.zx.shark.doFile.model.FileTagEnum;
import com.zx.shark.doFile.model.MyFile;
import com.zx.shark.doFile.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    FileService fileService;

    @Value("${file.absolute-location}")
    private String SystemPath;

    private Long parentId;
    private String username;
    private String filePath;
    private String fileName;   //文件名
    private String fileType;   //文件类型
    private Integer tag;    //文件标签，音乐、电影、文档、压缩文件、其他
    private Long size;   //文件大小
    private Integer needPass;   //是否为私密文件
    private String pass;      //密码
    private Timestamp createTime;   //创建时间

    @RequestMapping
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView("file");
        return modelAndView;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request){
        getFileFormRequest(request);  //从request中获取信息
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if (file.isEmpty()){
            return "文件为空";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = String.valueOf(authentication.getPrincipal());
        fileName = file.getOriginalFilename();
        filePath = SystemPath+username+"/"+FileTagEnum.getTagName(tag) +"/"+fileName;
        size = file.getSize();
        File dest = new File(filePath);
        //检查是否存在此目录
        if(!dest.getParentFile().exists()){
            System.out.println("文件不存在");
            dest.getParentFile().mkdirs();  //新建文件夹
        }
        try {
            file.transferTo(dest);
            MyFile myFile = new MyFile(parentId, username, fileName, fileType, tag,size, filePath, needPass, pass, createTime);
            //将内容储存在数据库中
            fileService.save(myFile);
            return "上传成功";
        } catch (IOException e) {
            System.out.println("上传"+fileName+"文件失败");
            return "上传失败";
        }
    }

    @RequestMapping("/uploadmore")
    @ResponseBody
    public String uploadmore(HttpServletRequest request){
        System.out.println("进入uploadmore");
        getFileFormRequest(request);   //从request获取文件的部分信息
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if(files.isEmpty()){
            return "文件为空";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = String.valueOf(authentication.getPrincipal());
        for(MultipartFile file : files){
            fileName = file.getOriginalFilename();
            fileType = fileName.substring(fileName.lastIndexOf("."));
            size = file.getSize();
            filePath = SystemPath+username+"/"+FileTagEnum.getTagName(tag)+"/"+fileName;
            File dest = new File(filePath);
            //检查是否存在此目录
            if(!dest.getParentFile().exists()){
                System.out.println("文件夹不存在，创建文件夹"+username);
                dest.getParentFile().mkdirs();  //新建文件夹
            }
            try {
                file.transferTo(dest);
                MyFile myFile = new MyFile(parentId, username, fileName, fileType, tag,size, filePath, needPass, pass, createTime);
                //将内容储存在数据库中
                fileService.save(myFile);
            } catch (IOException e) {
                return "上传"+file.getOriginalFilename()+"文件失败";
            }
        }
        return "上传成功";
    }

    /**
     * 根据id号来下载内容
     * @param response
     * @param id
     */
    @RequestMapping("/download")
    public void fileDownload(HttpServletResponse response,@RequestParam("id")Long id){
        MyFile myFile = new MyFile();
        myFile.setId(id);
        System.out.println("进入download");
        List<MyFile> list=fileService.list(myFile);
        DownloadFiles(response, list);
    }

    /**
     * 根据list和response返回文件给用户下载
     * @param response
     * @param list
     */
    private void DownloadFiles(HttpServletResponse response, List<MyFile> list) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        if(list.isEmpty()){  //判断是否查询成功
            return;
        }
        for (MyFile myFile : list){
            File file = new File(myFile.getFilePath());
            if(file.exists()) {
                response.setHeader("Content-Disposition", "attachment;filename=" + myFile.getFileName());  //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
                byte[] buff = new byte[1024];    //5.创建数据缓冲区
                BufferedInputStream bis = null;
                OutputStream os = null;
                try {
                    os = response.getOutputStream();
                    bis = new BufferedInputStream(new FileInputStream(file));
                    int i = bis.read(buff);
                    while (i != -1) {
                        os.write(buff, 0, buff.length);
                        os.flush();
                        i = bis.read(buff);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void getFileFormRequest(HttpServletRequest request){
        parentId = Long.valueOf(request.getParameter("parentId"));
        tag = Integer.valueOf(request.getParameter("tag"));
        needPass = Integer.valueOf(request.getParameter("needPass"));
        if(needPass==0){
            pass = request.getParameter("pass");
        }
        createTime = new Timestamp(System.currentTimeMillis());
    }
}
