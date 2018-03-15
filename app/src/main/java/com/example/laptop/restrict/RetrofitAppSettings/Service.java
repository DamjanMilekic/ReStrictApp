package com.example.laptop.restrict.RetrofitAppSettings;


import com.example.laptop.restrict.Model.Approval;
import com.example.laptop.restrict.Model.DataHome;
import com.example.laptop.restrict.Model.LoginRequest;
import com.example.laptop.restrict.Model.Person;
import com.example.laptop.restrict.Model.ProjectStatusLogin;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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



public interface Service {

    @GET ("api/me")
    Call<Person> getPerson(@Query("api_token") String apiToken);

    @GET ("api/projects")
    Call<DataHome> getProjects(@Query("api_token") String apiToken);

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
    Call <ResponseBody> loginToApplication(@Body LoginRequest loginRequest);

    @POST("api/login")
    Call <ProjectStatusLogin> loginToApp(@Body LoginRequest loginRequest);

    @POST("api/logout")
    Call<Person> logout(@Query("api_token") String apiToken);

}
