package com.example.laptop.restrict.Model;

/**
 * Created by durma on 14.2.18..
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationSettings implements Parcelable {

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

    protected NotificationSettings(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        userId = in.readString();
        drawingUploadPush = in.readString();
        drawingUploadEmail = in.readString();
        commentPush = in.readString();
        commentEmail = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<NotificationSettings> CREATOR = new Creator<NotificationSettings>() {
        @Override
        public NotificationSettings createFromParcel(Parcel in) {
            return new NotificationSettings(in);
        }

        @Override
        public NotificationSettings[] newArray(int size) {
            return new NotificationSettings[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(userId);
        parcel.writeString(drawingUploadPush);
        parcel.writeString(drawingUploadEmail);
        parcel.writeString(commentPush);
        parcel.writeString(commentEmail);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }
}