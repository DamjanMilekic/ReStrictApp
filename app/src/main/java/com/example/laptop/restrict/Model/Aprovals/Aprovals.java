package com.example.laptop.restrict.Model.Aprovals;

/**
 * Created by durma on 19.2.18..
 */

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aprovals implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DataAprovals> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public Aprovals() {
    }

    /**
     *
     * @param status
     * @param data
     */
    public Aprovals(String status, List<DataAprovals> data) {
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

    public List<DataAprovals> getData() {
        return data;
    }

    public void setData(List<DataAprovals> data) {
        this.data = data;
    }

}