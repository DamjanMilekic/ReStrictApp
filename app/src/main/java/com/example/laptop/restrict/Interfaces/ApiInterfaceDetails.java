package com.example.laptop.restrict.Interfaces;

import com.example.laptop.restrict.Model.PostCommentRequest;
import com.example.laptop.restrict.Model.ProjectStatusApprovals;
import com.example.laptop.restrict.Model.ProjectStatusComment;
import com.example.laptop.restrict.Model.ProjectStatusData;
import com.example.laptop.restrict.Model.ProjectStatusPostComment;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ivandjordjevic on 13.2.18..
 */

public interface ApiInterfaceDetails {

    @GET("projects/master/{id}")
    Call<ProjectStatusData> getVersions(@Path("id") int drawing_id, @Query("api_token") String api_key);

    @GET("comments/show/{id}")
    Call<ProjectStatusComment> getComments(@Path("id") int drawing_id, @Query("api_token") String api_key);

    @GET("approvals/show/{id}")
    Call<ProjectStatusApprovals> getApprovals(@Path("id") int drawing_id, @Query("api_token") String api_key);

    @POST("comments/store/")
    Call<ProjectStatusPostComment> setComment(@Body PostCommentRequest postCommentRequest);

}
