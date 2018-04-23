package com.example.laptop.restrict.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProjectStatusDeleteComment implements Serializable {

    @SerializedName("message")
    @Expose
    private String mMessage;
    @SerializedName("status")
    @Expose
    private String mStatus;

    public ProjectStatusDeleteComment(String mMessage, String mStatus) {
        this.mMessage = mMessage;
        this.mStatus = mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}

