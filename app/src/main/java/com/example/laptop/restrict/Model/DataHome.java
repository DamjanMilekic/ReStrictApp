package com.example.laptop.restrict.Model;


import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataHome implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Date> data = null;

    public final static Parcelable.Creator<DataHome> CREATOR = new Creator<DataHome>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DataHome createFromParcel(Parcel in) {
            return new DataHome(in);
        }

        public DataHome[] newArray(int size) {
            return (new DataHome[size]);        }

    };

    protected DataHome(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (com.example.laptop.restrict.Model.Date.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public DataHome() {
    }

    /**
     *
     * @param status
     * @param data
     */
    public DataHome(String status, List<Date> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Date> getData() {
        return data;
    }

    public void setData(List<Date> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}

