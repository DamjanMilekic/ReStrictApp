package com.example.laptop.restrict.Model;

import com.example.laptop.restrict.Fragments.DetailFragment;

/**
 * Created by ivandjordjevic on 20.2.18..
 */

public class PostCommentRequest {

    private int version_id;
    private String text;
    private String api_token;

    public PostCommentRequest(int version_id, String text, String api_token) {
        this.version_id = version_id;
        this.text = text;
        this.api_token = api_token;
    }

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
}
