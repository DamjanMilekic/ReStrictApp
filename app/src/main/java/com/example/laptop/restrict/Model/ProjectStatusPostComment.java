package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ivandjordjevic on 20.2.18..
 */

public class ProjectStatusPostComment implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public ProjectStatusPostComment() {

    }

    public ProjectStatusPostComment(String status, String message) {
        this.status = status;
        this.message = message;
    }

    protected ProjectStatusPostComment(Parcel in) {
        status = in.readString();
        message = in.readString();
    }

    public static final Creator<ProjectStatusPostComment> CREATOR = new Creator<ProjectStatusPostComment>() {
        @Override
        public ProjectStatusPostComment createFromParcel(Parcel in) {
            return new ProjectStatusPostComment(in);
        }

        @Override
        public ProjectStatusPostComment[] newArray(int size) {
            return new ProjectStatusPostComment[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(message);
    }
}
