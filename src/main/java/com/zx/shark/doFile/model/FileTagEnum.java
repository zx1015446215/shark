package com.zx.shark.doFile.model;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * 文件标签的枚举
 */
public enum FileTagEnum {
    FILE_TAG_ENUM_MUSIC (0,"音乐","/images/file/music.png"),
    FILE_TAG_ENUM_FILM(1,"视频","/images/file/film.png"),
    FILE_TAG_ENUM_PICTURE(2,"图片","/images/file/photo.png"),
    FILE_TAG_ENUM_WPS(3,"文档","/images/file/wps.png"),
    FILE_TAG_ENUM_ZIP(4,"压缩包","/images/file/zip.png"),
    FILE_TAG_ENUM_OTHER(5,"其他","/images/file/others.png"),
    FILE_TAG_ENUM_FOLDER(6,"文件夹","/images/file/文件.png");

    private Integer status;
    private String tag;
    private String fileImage;
    FileTagEnum(Integer status, String tag, String fileImage) {
        this.status = status;
        this.tag = tag;
        this.fileImage = fileImage;
    }

    public  static FileTagEnum getTagName(Integer index){
        for(FileTagEnum f:FileTagEnum.values()){
            if(f.getStatus() == index){
                return f;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(String fileImage) {
        this.fileImage = fileImage;
    }
}
