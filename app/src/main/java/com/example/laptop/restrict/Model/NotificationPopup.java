
package com.example.laptop.restrict.Model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationPopup implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DatumPopup> data = null;
    @SerializedName("seen_count")
    @Expose
    private String seenCount;
    public final static Parcelable.Creator<NotificationPopup> CREATOR = new Creator<NotificationPopup>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NotificationPopup createFromParcel(Parcel in) {
            return new NotificationPopup(in);
        }

        public NotificationPopup[] newArray(int size) {
            return (new NotificationPopup[size]);
        }

    }
            ;

    protected NotificationPopup(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (com.example.laptop.restrict.Model.DatumPopup.class.getClassLoader()));
        this.seenCount = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public NotificationPopup() {
    }

    /**
     *
     * @param status
     * @param data
     * @param seenCount
     */
    public NotificationPopup(String status, List<DatumPopup> data, String seenCount) {
        super();
        this.status = status;
        this.data = data;
        this.seenCount = seenCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NotificationPopup withStatus(String status) {
        this.status = status;
        return this;
    }

    public List<DatumPopup> getData() {
        return data;
    }

    public void setData(List<DatumPopup> data) {
        this.data = data;
    }

    public NotificationPopup withData(List<DatumPopup> data) {
        this.data = data;
        return this;
    }

    public String getSeenCount() {
        return seenCount;
    }

    public void setSeenCount(String seenCount) {
        this.seenCount = seenCount;
    }

    public NotificationPopup withSeenCount(String seenCount) {
        this.seenCount = seenCount;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(data);
        dest.writeValue(seenCount);
    }

    public int describeContents() {
        return  0;
    }

}

