package com.example.laptop.restrict.Model;

/**
 * Created by durma on 14.2.18..
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Person  implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     *
     */
    public Person() {
    }

    /**
     *
     * @param status
     * @param data
     */
    public Person(String status, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
