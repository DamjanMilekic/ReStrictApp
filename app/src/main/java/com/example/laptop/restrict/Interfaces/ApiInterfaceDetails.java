package com.example.laptop.restrict.Interfaces;

import android.support.v4.media.VolumeProviderCompat;

import com.example.laptop.restrict.Model.PostCommentRequest;
import com.example.laptop.restrict.Model.ProjectStatusApprovals;
import com.example.laptop.restrict.Model.ProjectStatusComment;
import com.example.laptop.restrict.Model.ProjectStatusData;
import com.example.laptop.restrict.Model.ProjectStatusPostComment;
import com.example.laptop.restrict.Model.ProjectStatusShare;


import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ivandjordjevic on 13.2.18..
 */

public interface ApiInterfaceDetails {

    @GET("api/projects/master/{id}")
    Call<ProjectStatusData> getVersions(@Path("id") int drawing_id, @Query("api_token") String api_key);

    @GET("api/comments/show/{id}")
    Call<ProjectStatusComment> getComments(@Path("id") int version_id, @Query("api_token") String api_key);

    @GET("api/approvals/show/{id}")
    Call<ProjectStatusApprovals> getApprovals(@Path("id") int drawing_id, @Query("api_token") String api_key);

    @POST("api/comments/store/")
    Call<ProjectStatusPostComment> setComment(@Body PostCommentRequest postCommentRequest);

    @POST("angular/share/create/")
    Call<ProjectStatusShare> share(@Body String json);

}
