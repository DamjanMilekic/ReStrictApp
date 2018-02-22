package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ivandjordjevic on 13.2.18..
 */

public class Drawing implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("versions")
    @Expose
    private List<Version> versions = null;

    public Drawing() {

    }

    public Drawing(Integer id, String sectionId, String title, String identifier, String createdAt, String updatedAt, List<Version> versions) {
        this.id = id;
        this.sectionId = sectionId;
        this.title = title;
        this.identifier = identifier;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.versions = versions;
    }

    protected Drawing(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        sectionId = in.readString();
        title = in.readString();
        identifier = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(sectionId);
        dest.writeString(title);
        dest.writeString(identifier);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Drawing> CREATOR = new Creator<Drawing>() {
        @Override
        public Drawing createFromParcel(Parcel in) {
            return new Drawing(in);
        }

        @Override
        public Drawing[] newArray(int size) {
            return new Drawing[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

}
