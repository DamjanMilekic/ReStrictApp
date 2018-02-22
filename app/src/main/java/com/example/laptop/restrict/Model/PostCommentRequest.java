package com.example.laptop.restrict.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.laptop.restrict.Fragments.DetailFragment;

/**
 * Created by ivandjordjevic on 20.2.18..
 */

public class PostCommentRequest implements Parcelable{

    private int version_id;
    private String text;
    private String api_token;

    public PostCommentRequest(int version_id, String text, String api_token) {
        this.version_id = version_id;
        this.text = text;
        this.api_token = api_token;
    }

    protected PostCommentRequest(Parcel in) {
        version_id = in.readInt();
        text = in.readString();
        api_token = in.readString();
    }

    public static final Creator<PostCommentRequest> CREATOR = new Creator<PostCommentRequest>() {
        @Override
        public PostCommentRequest createFromParcel(Parcel in) {
            return new PostCommentRequest(in);
        }

        @Override
        public PostCommentRequest[] newArray(int size) {
            return new PostCommentRequest[size];
        }
    };

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(version_id);
        parcel.writeString(text);
        parcel.writeString(api_token);
    }
}
