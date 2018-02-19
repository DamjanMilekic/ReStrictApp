package com.example.laptop.restrict.Model;

/**
 * Created by durma on 14.2.18..
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationSettings implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String userId;
    @Expose
    private String drawingUploadPush;
    @Expose
    private String drawingUploadEmail;
    @Expose
    private String commentPush;
    @Expose
    private String commentEmail;
    @Expose
    private String createdAt;
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public NotificationSettings() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param commentEmail
     * @param commentPush
     * @param createdAt
     * @param drawingUploadPush
     * @param userId
     * @param drawingUploadEmail
     */
    public NotificationSettings(Integer id, String userId, String drawingUploadPush, String drawingUploadEmail, String commentPush, String commentEmail, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.userId = userId;
        this.drawingUploadPush = drawingUploadPush;
        this.drawingUploadEmail = drawingUploadEmail;
        this.commentPush = commentPush;
        this.commentEmail = commentEmail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDrawingUploadPush() {
        return drawingUploadPush;
    }

    public void setDrawingUploadPush(String drawingUploadPush) {
        this.drawingUploadPush = drawingUploadPush;
    }

    public String getDrawingUploadEmail() {
        return drawingUploadEmail;
    }

    public void setDrawingUploadEmail(String drawingUploadEmail) {
        this.drawingUploadEmail = drawingUploadEmail;
    }

    public String getCommentPush() {
        return commentPush;
    }

    public void setCommentPush(String commentPush) {
        this.commentPush = commentPush;
    }

    public String getCommentEmail() {
        return commentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}