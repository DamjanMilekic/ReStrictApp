package com.example.laptop.restrict.Model;


import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrawingHome implements Parcelable
{

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
    private List<VersionHome> versions = null;
    public final static Parcelable.Creator<Drawing> CREATOR = new Creator<Drawing>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Drawing createFromParcel(Parcel in) {
            return new Drawing(in);
        }

        public Drawing[] newArray(int size) {
            return (new Drawing[size]);
        }

    }
            ;

    protected DrawingHome(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sectionId = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.identifier = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.versions, (com.example.laptop.restrict.Model.VersionHome.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public DrawingHome() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param versions
     * @param title
     * @param sectionId
     * @param createdAt
     * @param identifier
     */
    public DrawingHome(Integer id, String sectionId, String title, String identifier, String createdAt, String updatedAt, List<VersionHome> versions) {
        super();
        this.id = id;
        this.sectionId = sectionId;
        this.title = title;
        this.identifier = identifier;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.versions = versions;
    }

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

    public List<VersionHome> getVersions() {
        return versions;
    }

    public void setVersions(List<VersionHome> versions) {
        this.versions = versions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(sectionId);
        dest.writeValue(title);
        dest.writeValue(identifier);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeList(versions);
    }

    public int describeContents() {
        return  0;
    }

}

