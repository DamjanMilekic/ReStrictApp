
package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PivotPopup implements Parcelable
{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("seen")
    @Expose
    private String seen;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    public final static Parcelable.Creator<PivotPopup> CREATOR = new Creator<PivotPopup>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PivotPopup createFromParcel(Parcel in) {
            return new PivotPopup(in);
        }

        public PivotPopup[] newArray(int size) {
            return (new PivotPopup[size]);
        }

    }
            ;

    protected PivotPopup(Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.notificationId = ((String) in.readValue((String.class.getClassLoader())));
        this.seen = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public PivotPopup() {
    }

    /**
     *
     * @param updatedAt
     * @param notificationId
     * @param createdAt
     * @param userId
     * @param seen
     */
    public PivotPopup(String userId, String notificationId, String seen, String createdAt, String updatedAt) {
        super();
        this.userId = userId;
        this.notificationId = notificationId;
        this.seen = seen;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PivotPopup withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public PivotPopup withNotificationId(String notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public PivotPopup withSeen(String seen) {
        this.seen = seen;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public PivotPopup withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PivotPopup withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(notificationId);
        dest.writeValue(seen);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}

