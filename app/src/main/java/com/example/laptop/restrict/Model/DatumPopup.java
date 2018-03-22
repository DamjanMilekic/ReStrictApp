
package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumPopup implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("drawing_id")
    @Expose
    private String drawingId;
    @SerializedName("version_id")
    @Expose
    private String versionId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("pivot")
    @Expose
    private PivotPopup pivot;
    @SerializedName("type")
    @Expose
    private TypePopup type;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("comment")
    @Expose
    private Comment comment;
    public final static Parcelable.Creator<DatumPopup> CREATOR = new Creator<DatumPopup>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DatumPopup createFromParcel(Parcel in) {
            return new DatumPopup(in);
        }

        public DatumPopup[] newArray(int size) {
            return (new DatumPopup[size]);
        }

    }
            ;

    protected DatumPopup(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.typeId = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.drawingId = ((String) in.readValue((String.class.getClassLoader())));
        this.versionId = ((String) in.readValue((String.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
        this.pivot = ((PivotPopup) in.readValue((PivotPopup.class.getClassLoader())));
        this.type = ((TypePopup) in.readValue((TypePopup.class.getClassLoader())));
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.comment = ((Comment) in.readValue((Comment.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public DatumPopup() {
    }

    /**
     *
     * @param link
     * @param type
     * @param id
     * @param updatedAt
     * @param pivot
     * @param time
     * @param createdAt
     * @param userId
     * @param drawingId
     * @param versionId
     * @param comment
     * @param typeId
     * @param user
     */
    public DatumPopup(Integer id, String userId, String typeId, String link, String createdAt, String updatedAt, String drawingId, String versionId, String time, PivotPopup pivot, TypePopup type, User user, Comment comment) {
        super();
        this.id = id;
        this.userId = userId;
        this.typeId = typeId;
        this.link = link;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.drawingId = drawingId;
        this.versionId = versionId;
        this.time = time;
        this.pivot = pivot;
        this.type = type;
        this.user = user;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DatumPopup withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DatumPopup withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public DatumPopup withTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public DatumPopup withLink(String link) {
        this.link = link;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public DatumPopup withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DatumPopup withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getDrawingId() {
        return drawingId;
    }

    public void setDrawingId(String drawingId) {
        this.drawingId = drawingId;
    }

    public DatumPopup withDrawingId(String drawingId) {
        this.drawingId = drawingId;
        return this;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public DatumPopup withVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DatumPopup withTime(String time) {
        this.time = time;
        return this;
    }

    public PivotPopup getPivot() {
        return pivot;
    }

    public void setPivot(PivotPopup pivot) {
        this.pivot = pivot;
    }

    public DatumPopup withPivot(PivotPopup pivot) {
        this.pivot = pivot;
        return this;
    }

    public TypePopup getType() {
        return type;
    }

    public void setType(TypePopup type) {
        this.type = type;
    }

    public DatumPopup withType(TypePopup type) {
        this.type = type;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DatumPopup withUser(User user) {
        this.user = user;
        return this;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public DatumPopup withComment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(typeId);
        dest.writeValue(link);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(drawingId);
        dest.writeValue(versionId);
        dest.writeValue(time);
        dest.writeValue(pivot);
        dest.writeValue(type);
        dest.writeValue(user);
        dest.writeValue(comment);
    }

    public int describeContents() {
        return  0;
    }

}

