package com.zx.shark.doFile.model;


import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 文件管理的model
 */
@Entity
@Table(name = "file")
public class MyFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   //编号

    private Long parentId;  //父节点

    private String username;  //用户

    private String fileName;  //文件名

    private String fileType;   //文件类型

    private Integer tag;   //文件标签，例如音乐、图片、电影等

    private Long size;  //文件大小

    private String filePath;  //文件地址

    private Timestamp createTime;  //创建时间

    private String fileImage;  //文件图案

    public MyFile(Long id, Long parentId,String username, String fileName, String fileType, Integer tag, Long size, String filePath, Timestamp createTime, String fileImage) {
        this.id = id;
        this.parentId = parentId;
        this.username = username;
        this.fileName = fileName;
        this.fileType = fileType;
        this.tag = tag;
        this.size = size;
        this.filePath = filePath;
        this.createTime = createTime;
        this.fileImage = fileImage;
    }

    public MyFile(Long parentId,String username, String fileName, String fileType, Integer tag, Long size, String filePath, Timestamp createTime, String fileImage) {
        this.parentId = parentId;
        this.username = username;
        this.fileName = fileName;
        this.fileType = fileType;
        this.tag = tag;
        this.size = size;
        this.filePath = filePath;
        this.createTime = createTime;
        this.fileImage = fileImage;
    }

    public MyFile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(String fileImage) {
        this.fileImage = fileImage;
    }
}
