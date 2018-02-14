package com.example.laptop.restrict.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ivandjordjevic on 13.2.18..
 */

public class Version implements Serializable {

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
}
