package com.example.laptop.restrict.RetrofitAppSettings;


import com.example.laptop.restrict.Model.Aprovals.Aprovals;
import com.example.laptop.restrict.Model.Data;
import com.example.laptop.restrict.Model.Person;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by durma on 14.2.18..
 */

public interface ApiInterfaceAppSettings {

    @GET ("me")
    Call<Person> getPerson(@Query("api_token") String apiToken);

    @GET ("approvals/show/{version_id}")
    Call<Aprovals> getAprovals(@Query("api_token") String apiToken, @Path("version_id") int id_project);

}
