package com.example.laptop.restrict.Model;

/**
 * Created by durma on 14.2.18..
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Profile implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("member_title_id")
    @Expose
    private String memberTitleId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("push_token")
    @Expose
    private String pushToken;
    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public Profile() {
    }

    /**
     *
     * @param updatedAt
     * @param pushToken
     * @param id
     * @param os
     * @param phone
     * @param memberTitleId
     * @param createdAt
     * @param userId
     * @param image
     * @param companyId
     */
    public Profile(Integer id, String userId, String memberTitleId, String companyId, String phone, String image, String pushToken, String os, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.userId = userId;
        this.memberTitleId = memberTitleId;
        this.companyId = companyId;
        this.phone = phone;
        this.image = image;
        this.pushToken = pushToken;
        this.os = os;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberTitleId() {
        return memberTitleId;
    }

    public void setMemberTitleId(String memberTitleId) {
        this.memberTitleId = memberTitleId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}