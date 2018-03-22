
package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentPopup implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("version_id")
    @Expose
    private String versionId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("picture_path")
    @Expose
    private Object picturePath;
    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    public final static Parcelable.Creator<CommentPopup> CREATOR = new Creator<CommentPopup>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CommentPopup createFromParcel(Parcel in) {
            return new CommentPopup(in);
        }

        public CommentPopup[] newArray(int size) {
            return (new CommentPopup[size]);
        }

    }
            ;

    protected CommentPopup(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.versionId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.picturePath = ((Object) in.readValue((Object.class.getClassLoader())));
        this.notificationId = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public CommentPopup() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param notificationId
     * @param text
     * @param picturePath
     * @param createdAt
     * @param userId
     * @param versionId
     */
    public CommentPopup(Integer id, String versionId, String userId, String text, Object picturePath, String notificationId, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.versionId = versionId;
        this.userId = userId;
        this.text = text;
        this.picturePath = picturePath;
        this.notificationId = notificationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommentPopup withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public CommentPopup withVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CommentPopup withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentPopup withText(String text) {
        this.text = text;
        return this;
    }

    public Object getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(Object picturePath) {
        this.picturePath = picturePath;
    }

    public CommentPopup withPicturePath(Object picturePath) {
        this.picturePath = picturePath;
        return this;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public CommentPopup withNotificationId(String notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public CommentPopup withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CommentPopup withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(versionId);
        dest.writeValue(userId);
        dest.writeValue(text);
        dest.writeValue(picturePath);
        dest.writeValue(notificationId);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}

