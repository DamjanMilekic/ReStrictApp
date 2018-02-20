package com.example.laptop.restrict.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ivandjordjevic on 20.2.18..
 */

public class Client {

    @SerializedName("company_name")
    @Expose
    String companyName;

    public Client() {

    }

    public Client(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
