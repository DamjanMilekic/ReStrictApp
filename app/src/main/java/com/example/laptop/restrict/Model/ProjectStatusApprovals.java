package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ivandjordjevic on 20.2.18..
 */

public class ProjectStatusApprovals implements Parcelable{

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

    protected ProjectStatusApprovals(Parcel in) {
        status = in.readString();
        approvals = in.createTypedArrayList(Approval.CREATOR);
    }

    public static final Creator<ProjectStatusApprovals> CREATOR = new Creator<ProjectStatusApprovals>() {
        @Override
        public ProjectStatusApprovals createFromParcel(Parcel in) {
            return new ProjectStatusApprovals(in);
        }

        @Override
        public ProjectStatusApprovals[] newArray(int size) {
            return new ProjectStatusApprovals[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeTypedList(approvals);
    }
}
