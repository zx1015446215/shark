package com.zx.shark.model;
import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 文本内容
 */


public class ContentDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long cid;
    //标题
    private String title;
    //创建人id
    private Long created;
    //最近修改人id
    private Long modified;
    //内容
    private String content;
    //标签
    private String tags;
    //分类
    private String categories;
    //评论数量
    private Integer comments_num;
    //状态
    private Integer status;
    //点赞数量
    private Integer likes_count;
    //作者
    private String author;
    //创建时间
    private Timestamp gtm_create;
    //修改时间
    private Timestamp gtm_modified;

    public ContentDO(Long cid, String title, String content, String categories, String author, Timestamp gtm_modified) {
        this.cid = cid;
        this.title = title;
        this.content = content;
        this.categories = categories;
        this.author = author;
        this.gtm_modified = gtm_modified;
    }

    public ContentDO(Long cid, String title, Long created, Long modified, String content, String tags, String categories, Integer comments_num, Integer status, String author, Timestamp gtm_create, Timestamp gtm_modified) {
        this.cid = cid;
        this.title = title;
        this.created = created;
        this.modified = modified;
        this.content = content;
        this.tags = tags;
        this.categories = categories;
        this.comments_num = comments_num;
        this.status = status;
        this.author = author;
        this.gtm_create = gtm_create;
        this.gtm_modified = gtm_modified;
    }

    public ContentDO(String title, Long created, Long modified, String content, String categories, String author, Timestamp gtm_create, Timestamp gtm_modified) {
        this.title = title;
        this.created = created;
        this.modified = modified;
        this.content = content;
        this.categories = categories;
        this.author = author;
        this.gtm_create = gtm_create;
        this.gtm_modified = gtm_modified;
    }

    public ContentDO(Long cid, Integer likes_count){
        this.cid = cid;
        this.likes_count = likes_count;
    }

    public Integer getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(Integer likes_count) {
        this.likes_count = likes_count;
    }

    /**
     * 设置：
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }
    /**
     * 获取：
     */
    public Long getCid() {
        return cid;
    }
    /**
     * 设置：标题
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * 获取：标题
     */
    public String getTitle() {
        return title;
    }
    /**
     * 设置：创建人id
     */
    public void setCreated(Long created) {
        this.created = created;
    }
    /**
     * 获取：创建人id
     */
    public Long getCreated() {
        return created;
    }
    /**
     * 设置：最近修改人id
     */
    public void setModified(Long modified) {
        this.modified = modified;
    }
    /**
     * 获取：最近修改人id
     */
    public Long getModified() {
        return modified;
    }
    /**
     * 设置：内容
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 获取：内容
     */
    public String getContent() {
        return content;
    }
    /**
     * 设置：标签
     */
    public void setTags(String tags) {
        this.tags = tags;
    }
    /**
     * 获取：标签
     */
    public String getTags() {
        return tags;
    }
    /**
     * 设置：分类
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }
    /**
     * 获取：分类
     */
    public String getCategories() {
        return categories;
    }

    public Integer getComments_num() {
        return comments_num;
    }

    public void setComments_num(Integer comments_num) {
        this.comments_num = comments_num;
    }

    /**
     * 设置：状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：状态
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * 设置：作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * 获取：作者
     */
    public String getAuthor() {
        return author;
    }
    /**
     * 设置：创建时间
     */
    public void setGtm_create(Timestamp gtm_create) {
        this.gtm_create = gtm_create;
    }
    /**
     * 获取：创建时间
     */
    public Timestamp getGtm_create() {
        return gtm_create;
    }
    /**
     * 设置：修改时间
     */
    public void setGtm_modified(Timestamp gtm_modified) {
        this.gtm_modified = gtm_modified;
    }
    /**
     * 获取：修改时间
     */
    public Timestamp getGtm_modified() {
        return gtm_modified;
    }

    @Override
    public String toString() {
        return "ContentDO{" +
                "cid=" + cid +
                ", title='" + title + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", content='" + content + '\'' +
                ", tags='" + tags + '\'' +
                ", categories='" + categories + '\'' +
                ", commentsNum=" + comments_num +
                ", status=" + status +
                ", author='" + author + '\'' +
                ", gtmCreate=" + gtm_create +
                ", gtmModified=" + gtm_modified +
                '}';
    }
}

