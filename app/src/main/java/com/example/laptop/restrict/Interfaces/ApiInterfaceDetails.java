package com.example.laptop.restrict.Interfaces;

import com.example.laptop.restrict.Model.ProjectStatusData;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ivandjordjevic on 13.2.18..
 */

public interface ApiInterfaceDetails {

    @GET("projects/master/{id}")
    Call<ProjectStatusData> getVersions(@Path("id") int drawing_id, @Query("api_token") String api_key);

}
