package com.example.laptop.restrict.Model;


import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("project_id")
    @Expose
    private String projectId;
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
    @SerializedName("drawings")
    @Expose
    private List<DrawingHome> drawings = null;
    public final static Parcelable.Creator<Section> CREATOR = new Creator<Section>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        public Section[] newArray(int size) {
            return (new Section[size]);
        }

    }
            ;

    protected Section(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.projectId = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.identifier = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.drawings, (com.example.laptop.restrict.Model.Drawing.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Section() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param drawings
     * @param title
     * @param createdAt
     * @param projectId
     * @param identifier
     */
    public Section(Integer id, String projectId, String title, String identifier, String createdAt, String updatedAt, List<DrawingHome> drawings) {
        super();
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.identifier = identifier;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.drawings = drawings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public List<DrawingHome> getDrawings() {
        return drawings;
    }

    public void setDrawings(List<DrawingHome> drawings) {
        this.drawings = drawings;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(projectId);
        dest.writeValue(title);
        dest.writeValue(identifier);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeList(drawings);
    }

    public int describeContents() {
        return  0;
    }

}

