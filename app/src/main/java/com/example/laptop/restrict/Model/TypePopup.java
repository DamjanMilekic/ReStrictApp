
package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypePopup implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("text")
    @Expose
    private String text;
    public final static Parcelable.Creator<TypePopup> CREATOR = new Creator<TypePopup>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TypePopup createFromParcel(Parcel in) {
            return new TypePopup(in);
        }

        public TypePopup[] newArray(int size) {
            return (new TypePopup[size]);
        }

    }
            ;

    protected TypePopup(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.label = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public TypePopup() {
    }

    /**
     *
     * @param id
     * @param text
     * @param label
     */
    public TypePopup(Integer id, String label, String text) {
        super();
        this.id = id;
        this.label = label;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypePopup withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TypePopup withLabel(String label) {
        this.label = label;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TypePopup withText(String text) {
        this.text = text;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(label);
        dest.writeValue(text);
    }

    public int describeContents() {
        return  0;
    }

}

