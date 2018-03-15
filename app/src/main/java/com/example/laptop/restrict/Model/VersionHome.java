package com.example.laptop.restrict.Model;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionHome implements Parcelable
{

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
    public final static Parcelable.Creator<VersionHome> CREATOR = new Creator<VersionHome>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VersionHome createFromParcel(Parcel in) {
            return new VersionHome(in);
        }

        public VersionHome[] newArray(int size) {
            return (new VersionHome[size]);
        }

    }
            ;

    protected VersionHome(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.drawingId = ((String) in.readValue((String.class.getClassLoader())));
        this.parentId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.label = ((String) in.readValue((String.class.getClassLoader())));
        this.imageFile = ((String) in.readValue((String.class.getClassLoader())));
        this.pdfFile = ((String) in.readValue((String.class.getClassLoader())));
        this.issuedFor = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public VersionHome() {
    }

    /**
     *
     * @param updatedAt
     * @param pdfFile
     * @param id
     * @param parentId
     * @param issuedFor
     * @param createdAt
     * @param userId
     * @param imageFile
     * @param drawingId
     * @param label
     * @param type
     */
    public VersionHome(Integer id, String drawingId, Object parentId, String userId, String type, String label, String imageFile, String pdfFile, String issuedFor, String createdAt, String updatedAt) {
        super();
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(drawingId);
        dest.writeValue(parentId);
        dest.writeValue(userId);
        dest.writeValue(type);
        dest.writeValue(label);
        dest.writeValue(imageFile);
        dest.writeValue(pdfFile);
        dest.writeValue(issuedFor);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}

