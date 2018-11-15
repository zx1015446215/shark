package com.zx.shark.doFile.controller;

import com.zx.shark.doFile.model.FileTagEnum;
import com.zx.shark.doFile.model.MyFile;
import com.zx.shark.doFile.service.FileService;
import com.zx.shark.utils.JSONResult;
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
import java.util.*;

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
    private Timestamp createTime;   //创建时间

    //图片
    private String[] pics = {"JPG","JPEG","PNG","GIF","TIFF","BMP","DWG","PSD"};
    //文档
    private String[] docs = {"DOC","RTF", "XML", "HTML", "CSS", "JS","EML", "DBX", "PST", "XLS_DOC", "XLSX_DOCX"
            , "VSD", "MDB", "WPS","WPD","EPS", "PDF","QDF", "PWL","RAR", "JSP", "JAVA,CLASS","DOCX",
            "MF","EXE","CHM"};
    //视频
    private String[] videos = {"AVI", "RAM", "RM", "MPG","MOV","ASF","MP4","FLV","MID" };
    //压缩包
    private String[] zips = {"ZIP","JAR","CAB","GZ" ,"TAR","7Z","WAR"};
    //音乐
    private String[] musics = {"WAV", "MP3" };

    @RequestMapping
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView("file/index");
        List<MyFile> files = fileService.list(new MyFile());
        modelAndView.addObject("files",files);
        return modelAndView;
    }

    @RequestMapping("/test")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView("file");
        return modelAndView;
    }

    /**
     * 上传单个文件
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request){
        System.out.println("进入upload");
        parentId = 0L;
        createTime = new Timestamp(System.currentTimeMillis());
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if (file.isEmpty()){
            return "文件为空";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = String.valueOf(authentication.getPrincipal());
        fileName = file.getOriginalFilename();
        filePath = SystemPath+username+"/"+FileTagEnum.getTagName(tag).getTag() +"/"+fileName;
        size = file.getSize();
        File dest = new File(filePath);
        //检查是否存在此目录
        if(!dest.getParentFile().exists()){
            System.out.println("文件不存在");
            dest.getParentFile().mkdirs();  //新建文件夹
        }
        try {
            file.transferTo(dest);
            MyFile myFile = new MyFile(parentId, username, fileName, fileType, tag,size, filePath, createTime,FileTagEnum.getTagName(tag).getFileImage());
            //将内容储存在数据库中
            fileService.save(myFile);
            return "上传成功";
        } catch (IOException e) {
            System.out.println("上传"+fileName+"文件失败");
            return "上传失败";
        }
    }

    /**
     * 上传多个文件
     * @param request
     * @return
     */
    @RequestMapping("/uploadmore")
    @ResponseBody
    public JSONResult uploadmore(HttpServletRequest request){
        System.out.println("进入uploadmore");
        parentId = 0L;
        createTime = new Timestamp(System.currentTimeMillis());

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = String.valueOf(authentication.getPrincipal());
        for(MultipartFile file: files){
            fileName = file.getOriginalFilename();
            fileType = fileName.substring(fileName.lastIndexOf(".")+1).trim();
            //通过fileType来决定tag的值，通过tag的值来决定fileImage
            JudgeFileType();
            size = file.getSize();
            filePath = SystemPath+username+"/"+FileTagEnum.getTagName(tag).getTag()+"/"+fileName;
            File dest = new File(filePath);
            //检查是否存在此目录
            if(!dest.getParentFile().exists()){
                System.out.println("文件夹不存在，创建文件夹"+username);
                dest.getParentFile().mkdirs();  //新建文件夹
            }
            try {
                file.transferTo(dest);
                MyFile myFile = new MyFile(parentId, username, fileName, fileType, tag,size, filePath, createTime,FileTagEnum.getTagName(tag).getFileImage());
                //将内容储存在数据库中
                fileService.save(myFile);
            } catch (IOException e) {
                return JSONResult.errorMsg("上传"+fileName+"文件失败");
            }
        }
        return JSONResult.ok();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(@RequestParam("ids")String str){
        System.out.println("接收的信息:"+str);
        String[] ids = str.split(",");
        try {
            fileService.delete(ids);
        }catch (Exception e){
            return JSONResult.errorMsg("删除失败");
        }
        return JSONResult.ok();
    }

    @RequestMapping("/update")
    @ResponseBody
    public JSONResult update(){
        return JSONResult.ok();
    }


    /**
     * 判断文件类型
     */
    private void JudgeFileType() {

        if(Arrays.asList(pics).contains(fileType.toUpperCase())){  //若图片中存在
            tag = 2;
        }else if(Arrays.asList(docs).contains(fileType.toUpperCase())){  //若文档中存在
            tag = 3;
        }else if(Arrays.asList(videos).contains(fileType.toUpperCase())){ //若视频中存在
            tag = 1;
        }else if(Arrays.asList(zips).contains(fileType.toUpperCase())) {  //若压缩包中存在
            tag = 4;
        }else if(Arrays.asList(musics).contains(fileType.toUpperCase())){  //若音乐中存在
            tag = 0;
        }else{  //在其它中
            tag = 5;
        }
    }

    /**
     * 根据id号来下载内容
     * @param response
     * @param id
     */
    @RequestMapping("/download")
    public void fileDownload(HttpServletResponse response, @RequestParam(value = "id",required = false)Long id){
        System.out.println("进入download");
        MyFile myFile = new MyFile();
        if(id!=null) {  //如果存在参数添加
            myFile.setId(id);
        }
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

    @RequestMapping("/findFilesByObject")
    @ResponseBody
    public List<MyFile> find(@RequestParam(value = "tag",required = false)Integer tag){
        MyFile myFile = new MyFile();
        if(tag!=null){
            myFile.setTag(tag);
        }
        List<MyFile> list = fileService.list(myFile);
        return list;
    }


}
