package com.example.ahmed.jobtask;

import com.example.ahmed.jobtask.data.Example;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {
    @POST("oauth/v2/token")
    @FormUrlEncoded
    Call<Credential> getAccessToken(@Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("grant_type") String grantType,
                                    @Field("username") String username,
                                    @Field("password") String password);

    @GET("oauth/v2/token")
    Call<Credential> refreshAccessToken(@Query("client_id") String clientId,
                                    @Query("client_secret") String clientSecret,
                                    @Query("grant_type") String grantType,
                                    @Query("refresh_token") String refreshToken);

    @GET("v1/products")
    Call<Example> getAllProducts(@Header("Authorization") String accessToken,
                                 @Query("page") int limit);



//    @GET("/dresscode/web/app_dev.php/api/v1/users/{id}")
//    @Headers({
//            "Content-Type:application/json"
//    })
//    Call<Account> getAdmin(@Path("id") String id,
//                              @Header("Authorization") String accessToken);
//
//    @POST("/dresscode/web/app_dev.php/api/v1/users/")
//    @Headers({
//            "Content-Type:application/json"
//    })
//    @FormUrlEncoded
//    Call<Account> createAdmin(@Header("Authorization") String accessToken,
//                              @Field("username") String username,
//                              @Field("email") String email,
//                              @Field("plainPassword") String password,
//                              @Field("localeCode") String localeCode);
}