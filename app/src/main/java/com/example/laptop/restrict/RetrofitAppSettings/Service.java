package com.example.laptop.restrict.RetrofitAppSettings;


import com.example.laptop.restrict.Model.Approval;
import com.example.laptop.restrict.Model.Person;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by durma on 14.2.18..
 */

public interface Service {

    @GET ("me")
    Call<Person> getPerson(@Query("api_token") String apiToken);

    @GET ("approvals/show/{version_id}")
    Call<Approval> getAprovals(@Path("version_id")int versionId , @Query("api_token") String apiToken);

   @FormUrlEncoded
    @POST("me/update")
    Call<Person> postPerson(@Field("first_name") String first_name,
                            @Field("last_name") String last_name,
                            @Field("title") String title,
                            @Field("email") String email,
                            @Field("phone") String phone,
                            @Field("image") String image,
                            @Query("api_token") String apiToken);

/*    @FormUrlEncoded
    @POST("me/update")
    Call<Person> postPerson(@Part("form") RequestBody body
                            @Query("api_token") String apiToken);*/

}
