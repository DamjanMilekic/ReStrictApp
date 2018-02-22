package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ivandjordjevic on 19.2.18..
 */

public class ProjectStatusComment implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<Comment> comments;

    public ProjectStatusComment() {

    }

    public ProjectStatusComment(String status, ArrayList<Comment> data) {
        this.status = status;
        this.comments = data;
    }

    protected ProjectStatusComment(Parcel in) {
        status = in.readString();
        comments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<ProjectStatusComment> CREATOR = new Creator<ProjectStatusComment>() {
        @Override
        public ProjectStatusComment createFromParcel(Parcel in) {
            return new ProjectStatusComment(in);
        }

        @Override
        public ProjectStatusComment[] newArray(int size) {
            return new ProjectStatusComment[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeTypedList(comments);
    }
}
