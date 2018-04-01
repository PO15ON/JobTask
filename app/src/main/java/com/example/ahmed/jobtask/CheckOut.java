package com.example.ahmed.jobtask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.ahmed.jobtask.cart.channel.Channel;
import com.example.ahmed.jobtask.credentials.Account;
import com.example.ahmed.jobtask.credentials.ApiClient;
import com.example.ahmed.jobtask.credentials.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOut extends AppCompatActivity {

    private static final String TAG = "admin";
    String variantCode, accessToken, email, productCode;
    TextView userText, emailText, idText, priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        userText = findViewById(R.id.check_user);
        emailText = findViewById(R.id.check_email);
        idText = findViewById(R.id.check_id);
        priceText = findViewById(R.id.variant_price);

        accessToken = MainActivity.accessToken;
        variantCode = getIntent().getStringExtra("variantCode");
        productCode = getIntent().getStringExtra("productCode");


        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<Channel> channelCall = apiInterface.createChannel(accessToken);
        channelCall.enqueue(new Callback<Channel>() {
            @Override
            public void onResponse(Call<Channel> call, Response<Channel> response) {
                Log.i(TAG, "onResponse: response(channel) = " + response);
                response.body().setCode("ahmed");
                response.body().setName("ahmed");

                /*GET THE CHANNEL*/
//                Call<Channel> getCartChannel = apiInterface.getChannel(accessToken, "ahmed");
//                getCartChannel.enqueue(new Callback<Channel>() {
//                    @Override
//                    public void onResponse(Call<Channel> call, Response<Channel> response) {
//                        Log.i(TAG, "onResponse: getChannel = " + response);
//                        Log.i(TAG, "onResponse: getChannelId = " + response.body().getId());
//                    }
//
//                    @Override
//                    public void onFailure(Call<Channel> call, Throwable t) {
//
//                    }
//                });

                Log.i(TAG, "onResponse: code " + response.body().getCode());


                /*GET USER INFO*/

                Call<Account> getUserInfo = apiInterface.getAdmin(accessToken, MainActivity.adminId);
                getUserInfo.enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        Log.i(TAG, "onResponse: response (Account)= " + response);
                        String username = response.body().getUsername();
                        Integer id = response.body().getId();
                        email = response.body().getEmail();

                        userText.setText(username);
                        idText.setText(Integer.toString(id));
                        emailText.setText(email);

                        /*CREATE CART*/

//                        Call<Cart> createNewCart = apiInterface.createCart(accessToken, "api@example.com", "ahmed", "en_US");
//
//                        createNewCart.enqueue(new Callback<Cart>() {
//                            @Override
//                            public void onResponse(Call<Cart> call, Response<Cart> response) {
//                                Log.i(TAG, "onResponse: email = " + email);
//                                if (response.isSuccessful()) {
//
//                                    Log.i(TAG, "onResponse: accessToken = " + accessToken);
//                                    Log.i(TAG, "onResponse: email = " + email);
//                                    Log.i("cart", "onResponse: cart response = " + response);
//                                    if (response.code() != 201) {
//                                        Toast.makeText(CheckOut.this, "Error: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//                                } else {
//                                    CartError error = ErrorUtils.parseCartError(response);
//                                    // … and use it to show error information
//
//                                    Log.i(TAG, "onResponse cart-error: " + response);
//                                    Log.i(TAG, "onResponse: cart-error(customer): " + error.getErrors().getChildren().getCustomer().getErrors());
//                                    Log.i(TAG, "onResponse: cart-error(localeCode): " + error.getErrors().getChildren().getLocaleCode().getErrors());
//                                    Log.i(TAG, "onResponse: cart-error(channel): " + error.getErrors().getChildren().getChannel().getErrors());
//
//                                }
////                                Log.i(TAG, "onResponse: errors = " + response);
////                                Log.i(TAG, "onResponse: cart-id = " + response.body().getId());
////                                Log.i(TAG, "onResponse: " + response.body().getChannel());
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<Cart> call, Throwable t) {
//
//                            }
//                        });
                        /*END CREATE CART*/


                        Log.i(TAG, "onResponse: product = " + productCode);
                        Log.i(TAG, "onResponse: variantCOde = " + variantCode);
//                        Call<Variants> getVariant = apiInterface.getVariants(accessToken, productCode, variantCode);
//                        getVariant.enqueue(new Callback<Variants>() {
//                            @Override
//                            public void onResponse(Call<Variants> call, Response<Variants> response) {
//                                Log.i(TAG, "onResponse: getVariant = " + response);
//                                if (response.code() != 200) {
//                                    Log.i(TAG, "onResponse: Error: " + response.code());
//                                    return;
//                                }
//                                if (response.body().getChannelPricings().size() != 0) {
//                                    Log.i(TAG, "onResponse: prices = " + response.body().getChannelPricings());
//                                } else {
//                                    Log.i(TAG, "onResponse: no prices");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Variants> call, Throwable t) {
//
//                            }
//                        });
                        Call<com.example.ahmed.jobtask.allvariants.Variants> getAllVariants = apiInterface.getAllVariants(accessToken, productCode);
                        getAllVariants.enqueue(new Callback<com.example.ahmed.jobtask.allvariants.Variants>() {
                            @Override
                            public void onResponse(Call<com.example.ahmed.jobtask.allvariants.Variants> call, Response<com.example.ahmed.jobtask.allvariants.Variants> response) {

                                int size = response.body().getEmbedded().getItems().size();
                                Log.i(TAG, "onResponse: size = " + size);
                                Log.i(TAG, "onResponse: productCode = " + productCode);

                                boolean found = false;

                                for (int i = 0; i < size; i++) {
                                    int sizeValue = response.body().getEmbedded().getItems().get(i).getOptionValues().size();
                                    for (int j = 0; j < sizeValue; j++) {
                                        if (found) break;
                                        Log.i(TAG, "onResponse: item = " + response.body().getEmbedded().getItems().get(i).getOptionValues().get(j).getCode());

                                        if (response.body().getEmbedded().getItems().get(i).getOptionValues().get(j).getCode().equals(variantCode)) {
                                            Integer price = response.body().getEmbedded().getItems().get(i).getChannelPricings().getUSWEB().getPrice();
                                            priceText.setText(Integer.toString(price));
                                            Log.i(TAG, "onResponse: price = " + price);
                                            found = true;
                                            break;
                                        }
                                    }
                                    // TODO: 2/16/2018 variantCOdes are different
                                }
                                Log.i(TAG, "onResponse: getAllVariants = " + response);
                                Log.i(TAG, "onResponse: " + response.body().getEmbedded().getItems().get(0).getChannelPricings().getUSWEB().getPrice());
                                Log.i(TAG, "onResponse: type = " + response.body().getEmbedded().getItems().get(0).getChannelPricings().getUSWEB().getPrice().getClass().getName());
                            }

                            @Override
                            public void onFailure(Call<com.example.ahmed.jobtask.allvariants.Variants> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Channel> call, Throwable t) {

            }
        });




    }
}
