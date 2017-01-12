package com.liumw.chargebaby.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumw
 * @date 2016/8/15 0015
 */
public class ReplyVo implements Serializable {
    private Long id;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;
    /**评论内容*/
    private String info;
    /**作者用户名*/
    private String author;
    /**作者ID*/
    private Long authorId;
    /**回复对象*/
    private String reply;
    /**回复对象ID*/
    private Long replyId;
    /**充电桩编码*/
    private String chargeNo;

    /**所属评论*/
    private Long fatherCommentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getFatherCommentId() {
        return fatherCommentId;
    }

    public String getChargeNo() {
        return chargeNo;
    }

    public void setChargeNo(String chargeNo) {
        this.chargeNo = chargeNo;
    }


    public void setFatherCommentId(Long fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }
}
