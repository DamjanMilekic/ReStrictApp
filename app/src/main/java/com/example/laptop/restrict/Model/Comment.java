package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ivandjordjevic on 19.2.18..
 */

public class Comment implements Parcelable{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("version_id")
    @Expose
    private int versionId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("drawing_path")
    @Expose
    private String drawingPath;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;

    protected Comment(Parcel in) {
        id = in.readInt();
        versionId = in.readInt();
        text = in.readString();
        time = in.readString();
        userId = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        drawingPath = in.readString();
        userAvatar = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDrawingPath() {
        return drawingPath;
    }

    public void setDrawingPath(String drawingPath) {
        this.drawingPath = drawingPath;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(versionId);
        parcel.writeString(text);
        parcel.writeString(time);
        parcel.writeInt(userId);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(drawingPath);
        parcel.writeString(userAvatar);
    }
}
