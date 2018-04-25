package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ivandjordjevic on 20.2.18..
 */

public class Approval implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("client")
    @Expose
    private Object client;
    @SerializedName("uploader")
    @Expose
    private Boolean uploader;
    @SerializedName("approval_id")
    @Expose
    private Integer approvalId;
    @SerializedName("approved")
    @Expose
    private Integer approved;
    @SerializedName("composite_key")
    @Expose
    private String compositeKey;

    public Approval() {

    }

    public Approval(Integer userId, String email, String title, String firstName, String lastName, String image, String role, String companyName, String phone, Object client, Boolean uploader, Integer approvalId, Integer approved, String compositeKey) {
        this.userId = userId;
        this.email = email;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.role = role;
        this.companyName = companyName;
        this.phone = phone;
        this.client = client;
        this.uploader = uploader;
        this.approvalId = approvalId;
        this.approved = approved;
        this.compositeKey = compositeKey;
    }

    protected Approval(Parcel in) {
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        email = in.readString();
        title = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        image = in.readString();
        role = in.readString();
        companyName = in.readString();
        phone = in.readString();
        byte tmpUploader = in.readByte();
        uploader = tmpUploader == 0 ? null : tmpUploader == 1;
        if (in.readByte() == 0) {
            approvalId = null;
        } else {
            approvalId = in.readInt();
        }
        if (in.readByte() == 0) {
            approved = null;
        } else {
            approved = in.readInt();
        }
        compositeKey = in.readString();
    }

    public static final Creator<Approval> CREATOR = new Creator<Approval>() {
        @Override
        public Approval createFromParcel(Parcel in) {
            return new Approval(in);
        }

        @Override
        public Approval[] newArray(int size) {
            return new Approval[size];
        }
    };

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getClient() {
        return client;
    }

    public void setClient(Object client) {
        this.client = client;
    }

    public Boolean getUploader() {
        return uploader;
    }

    public void setUploader(Boolean uploader) {
        this.uploader = uploader;
    }

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public String getCompositeKey() {
        return compositeKey;
    }

    public void setCompositeKey(String compositeKey) {
        this.compositeKey = compositeKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (userId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(userId);
        }
        parcel.writeString(email);
        parcel.writeString(title);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(image);
        parcel.writeString(role);
        parcel.writeString(companyName);
        parcel.writeString(phone);
        parcel.writeByte((byte) (uploader == null ? 0 : uploader ? 1 : 2));
        if (approvalId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(approvalId);
        }
        if (approved == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(approved);
        }
        parcel.writeString(compositeKey);
    }
}
