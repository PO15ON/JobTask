package com.example.ahmed.jobtask.credentials;

import com.example.ahmed.jobtask.data.allProducts.AllProducts;
import com.example.ahmed.jobtask.singleProduct.SingleProduct;
import com.example.ahmed.jobtask.variants.Variants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @FormUrlEncoded
    @POST("v1/users/")
    Call<Account> createAdmin(@Header("Authorization") String accessToken,
                              @Field("username") String username,
                              @Field("email") String email,
                              @Field("plainPassword") String password,
                              @Field("localeCode") String localeCode,
                              @Field("enabled") boolean enabled);

    @GET("v1/users/{id}")
    Call<Account> getAdmin(@Header("Authorization") String accessToken,
                           @Path("id") String id);


    @GET("v1/products")
    Call<AllProducts> getAllProducts(@Header("Authorization") String accessToken,
                                     @Query("limit") int limit);


    @GET("v1/products/{code}")
    Call<SingleProduct> getProduct(@Header("Authorization") String accessToken,
                                   @Path("code") String code);

    @GET("v1/products/{productCode}/variants/{code}")
    Call<Variants> getVariants(@Header("Authorization") String accessToken,
                               @Path("productCode") String productCode,
                               @Path("code") String variantCode);
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