package com.zx.shark.doFile.model;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * 文件标签的枚举
 */
public enum FileTagEnum {
    FILE_TAG_ENUM_MUSIC (0,"音乐"),
    FILE_TAG_ENUM_FILM(1,"视频"),
    FILE_TAG_ENUM_PICTURE(2,"图片"),
    FILE_TAG_ENUM_WPS(3,"文档"),
    FILE_TAG_ENUM_ZIP(4,"压缩包"),
    FILE_TAG_ENUM_OTHER(5,"其他");

    private Integer status;
    private String tag;

    FileTagEnum(Integer status, String tag) {
        this.status = status;
        this.tag = tag;
    }

    public  static String getTagName(Integer index){
        for(FileTagEnum f:FileTagEnum.values()){
            if(f.getStatus() == index){
                return f.getTag();
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
}
