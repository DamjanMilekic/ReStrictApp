package com.example.laptop.restrict.Model;


import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Date implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("archived")
    @Expose
    private String archived;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("sections")
    @Expose
    private List<Section> sections = null;
    public final static Parcelable.Creator<Date> CREATOR = new Creator<Date>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Date createFromParcel(Parcel in) {
            return new Date(in);
        }

        public Date[] newArray(int size) {
            return (new Date[size]);
        }

    }
            ;

    protected Date(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.identifier = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.archived = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.sections, (com.example.laptop.restrict.Model.Section.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Date() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param sections
     * @param title
     * @param archived
     * @param createdAt
     * @param identifier
     */
    public Date(Integer id, String identifier, String title, String archived, String createdAt, String updatedAt, List<Section> sections) {
        super();
        this.id = id;
        this.identifier = identifier;
        this.title = title;
        this.archived = archived;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sections = sections;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArchived() {
        return archived;
    }

    public void setArchived(String archived) {
        this.archived = archived;
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

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(identifier);
        dest.writeValue(title);
        dest.writeValue(archived);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeList(sections);
    }

    public int describeContents() {
        return  0;
    }

}

