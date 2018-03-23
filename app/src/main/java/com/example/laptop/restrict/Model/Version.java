package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by ivandjordjevic on 13.2.18..
 */

public class Version implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("drawing_id")
    @Expose
    private String drawingId;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("image_file")
    @Expose
    private String imageFile;
    @SerializedName("pdf_file")
    @Expose
    private String pdfFile;
    @SerializedName("issued_for")
    @Expose
    private String issuedFor;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("sub_versions")
    @Expose
    private Integer[] subVersions;
    @SerializedName("user")
    @Expose
    private User user;

    public Version() {

    }

    public Version(Integer id, String drawingId, Object parentId, String userId, String type, String label, String imageFile, String pdfFile, String issuedFor, String createdAt, String updatedAt, Integer[] subVersions, User user) {
        this.id = id;
        this.drawingId = drawingId;
        this.parentId = parentId;
        this.userId = userId;
        this.type = type;
        this.label = label;
        this.imageFile = imageFile;
        this.pdfFile = pdfFile;
        this.issuedFor = issuedFor;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subVersions = subVersions;
        this.user = user;
    }

    protected Version(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        drawingId = in.readString();
        userId = in.readString();
        type = in.readString();
        label = in.readString();
        imageFile = in.readString();
        pdfFile = in.readString();
        issuedFor = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Version> CREATOR = new Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel in) {
            return new Version(in);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrawingId() {
        return drawingId;
    }

    public void setDrawingId(String drawingId) {
        this.drawingId = drawingId;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getIssuedFor() {
        return issuedFor;
    }

    public void setIssuedFor(String issuedFor) {
        this.issuedFor = issuedFor;
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

    public Integer[] getSubVersions() {
        return subVersions;
    }

    public void setSubVersions(Integer[] subVersions) {
        this.subVersions = subVersions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(drawingId);
        parcel.writeString(userId);
        parcel.writeString(type);
        parcel.writeString(label);
        parcel.writeString(imageFile);
        parcel.writeString(pdfFile);
        parcel.writeString(issuedFor);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeParcelable(user, i);
    }

    @Override
    public String toString() {
        return "Version{" +
                "id=" + id +
                ", drawingId='" + drawingId + '\'' +
                ", parentId=" + parentId +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", imageFile='" + imageFile + '\'' +
                ", pdfFile='" + pdfFile + '\'' +
                ", issuedFor='" + issuedFor + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", subVersions=" + Arrays.toString(subVersions) +
                ", user=" + user +
                '}';
    }

}
