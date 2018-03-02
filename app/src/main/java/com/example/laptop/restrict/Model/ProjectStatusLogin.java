package com.example.laptop.restrict.Model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ivandjordjevic on 26.2.18..
 */

public class ProjectStatusLogin implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public ProjectStatusLogin() {

    }

    public ProjectStatusLogin(String status) {
        this.status = status;
        this.token = null;
        this.userId = null;
        this.avatar = null;
    }

    public ProjectStatusLogin(String status, String token, Integer userId, String avatar) {
        this.status = status;
        this.token = token;
        this.userId = userId;
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "ProjectStatusLogin{" +
                "status='" + status + '\'' +
                ", token='" + token + '\'' +
                ", userId=" + userId +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
