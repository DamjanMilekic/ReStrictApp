
package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePopup implements Parcelable
{

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
    public final static Parcelable.Creator<ProfilePopup> CREATOR = new Creator<ProfilePopup>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProfilePopup createFromParcel(Parcel in) {
            return new ProfilePopup(in);
        }

        public ProfilePopup[] newArray(int size) {
            return (new ProfilePopup[size]);
        }

    }
            ;

    protected ProfilePopup(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.memberTitleId = ((String) in.readValue((String.class.getClassLoader())));
        this.companyId = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.pushToken = ((String) in.readValue((String.class.getClassLoader())));
        this.os = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ProfilePopup() {
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
    public ProfilePopup(Integer id, String userId, String memberTitleId, String companyId, String phone, String image, String pushToken, String os, String createdAt, String updatedAt) {
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

    public ProfilePopup withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ProfilePopup withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getMemberTitleId() {
        return memberTitleId;
    }

    public void setMemberTitleId(String memberTitleId) {
        this.memberTitleId = memberTitleId;
    }

    public ProfilePopup withMemberTitleId(String memberTitleId) {
        this.memberTitleId = memberTitleId;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public ProfilePopup withCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ProfilePopup withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProfilePopup withImage(String image) {
        this.image = image;
        return this;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public ProfilePopup withPushToken(String pushToken) {
        this.pushToken = pushToken;
        return this;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public ProfilePopup withOs(String os) {
        this.os = os;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ProfilePopup withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProfilePopup withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(memberTitleId);
        dest.writeValue(companyId);
        dest.writeValue(phone);
        dest.writeValue(image);
        dest.writeValue(pushToken);
        dest.writeValue(os);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}

