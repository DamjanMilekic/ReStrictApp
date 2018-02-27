package com.example.laptop.restrict.RetrofitAppSettings;


import com.example.laptop.restrict.Model.Approval;
import com.example.laptop.restrict.Model.LoginRequest;
import com.example.laptop.restrict.Model.Person;
import com.example.laptop.restrict.Model.ProjectStatusLogin;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by durma on 14.2.18..
 */

public interface Service {

    @GET ("api/me")
    Call<Person> getPerson(@Query("api_token") String apiToken);

    @GET ("api/approvals/show/{version_id}")
    Call<Approval> getAprovals(@Path("version_id")int versionId , @Query("api_token") String apiToken);

    @FormUrlEncoded
    @POST("api/me/update")
    Call<Person> postPerson(@Field("first_name") String first_name,
                            @Field("last_name") String last_name,
                            @Field("title") String title,
                            @Field("email") String email,
                            @Field("phone") String phone,
                            @Query("api_token") String apiToken);

    @Multipart
    @POST("img-save-to-file")
    Call<Person> postPicture(@Part MultipartBody.Part file, @Query("api_token") String apiToken);

    @POST("api/login")
    Call <ProjectStatusLogin> loginToApp(@Body LoginRequest loginRequest);

}
