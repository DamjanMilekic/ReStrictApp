package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ivandjordjevic on 13.2.18..
 */

public class ProjectStatusData implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public ProjectStatusData() {

    }

    public ProjectStatusData(String status, Data data) {
        this.status = status;
        this.data = data;
    }

    protected ProjectStatusData(Parcel in) {
        status = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<ProjectStatusData> CREATOR = new Creator<ProjectStatusData>() {
        @Override
        public ProjectStatusData createFromParcel(Parcel in) {
            return new ProjectStatusData(in);
        }

        @Override
        public ProjectStatusData[] newArray(int size) {
            return new ProjectStatusData[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeParcelable(data, i);
    }
}
