package com.liumw.chargebaby.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liumw
 * @date 2016/8/15 0015
 */
public class CommentVo implements Serializable {
    private Long id;
    /**头像*/
    private int  portraitId;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;
    /**回复数量，针对本条评论的回复数,当为null时，即为给评论的回复*/
    private Integer replyNum;
    /**评论内容*/
    private String info;
    /**作者用户名*/
    private String author;
    /**作者ID*/
    private Long authorId;
    /**充电桩编码*/
    private String chargeNo;

    /**回复列表*/
    private List<ReplyVo> replyVoList = new ArrayList<ReplyVo>();

    public CommentVo(){}
    public CommentVo(String author, String info,Date createTime) {
        this.createTime = createTime;
        this.info = info;
        this.author = author;
    }

    public int getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(int portraitId) {
        this.portraitId = portraitId;
    }

    public boolean hasReply() {
        return replyVoList.size() > 0;
    }

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

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
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

    public String getChargeNo() {
        return chargeNo;
    }

    public void setChargeNo(String chargeNo) {
        this.chargeNo = chargeNo;
    }

    public List<ReplyVo> getReplyVoList() {
        return replyVoList;
    }

    public void setReplyVoList(List<ReplyVo> replyVoList) {
        this.replyVoList = replyVoList;
    }
}
