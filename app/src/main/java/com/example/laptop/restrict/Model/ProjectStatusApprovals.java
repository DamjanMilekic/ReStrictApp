package com.example.laptop.restrict.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ivandjordjevic on 20.2.18..
 */

public class ProjectStatusApprovals {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<Approval> approvals;

    public ProjectStatusApprovals() {

    }

    public ProjectStatusApprovals(String status, ArrayList<Approval> approvals) {
        this.status = status;
        this.approvals = approvals;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Approval> getApprovals() {
        return approvals;
    }

    public void setApprovals(ArrayList<Approval> approvals) {
        this.approvals = approvals;
    }
}
