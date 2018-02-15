package com.example.ahmed.jobtask;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.jobtask.credentials.ApiClient;
import com.example.ahmed.jobtask.credentials.ApiInterface;
import com.example.ahmed.jobtask.singleProduct.SingleProduct;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {

    TextView name,code,variants,stock;
    Button orderButton;
    String productCode;

    static Toast mToast;
    static int errorCode = 0;
    int productStock;
    String variantName;
    String productName;
    String[] variantsArray;

    public static final String TAG = "Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        name = findViewById(R.id.product_name);
        code = findViewById(R.id.product_code);
        variants = findViewById(R.id.product_details);
        stock = findViewById(R.id.product_stock);
        orderButton = findViewById(R.id.order_button);
        mToast = Toast.makeText(this, "Error: " + errorCode, Toast.LENGTH_SHORT);
        //retrieve product code to make the request
        productCode = getIntent().getStringExtra("code");

//        variantsArray = new String[100];
        /*HANDLE ORDER REQUEST*/
        orderButton.setOnClickListener(new View.OnClickListener() {

            String selectedVariant;

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
                builder.setTitle(productName)
                        .setSingleChoiceItems(variantsArray, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.i(TAG, "onClick: " + variantsArray[i]);
                                selectedVariant = variantsArray[i];
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ProductDetails.this, "Order Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                        .setPositiveButton("Order Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ProductDetails.this, CheckOut.class);
                                intent.putExtra("variantCode", selectedVariant);
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        /*START THE REQUEST*/
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SingleProduct> singleProductCall = apiInterface.getProduct(MainActivity.accessToken, productCode);
        singleProductCall.enqueue(new Callback<SingleProduct>() {
            @Override
            public void onResponse(Call<SingleProduct> call, Response<SingleProduct> response) {
                Log.d(TAG, "onResponse: accessToken = " + MainActivity.accessToken);
                Log.d(TAG, "onResponse: productCode = " + productCode);
                Log.d(TAG, "onResponse: response= " + response);
                Log.d(TAG, "onResponse: " + response.body().getName());

                if (response.code() != 200) {
                    errorCode = response.code();
                    mToast.show();
                    return;
                }
                productName = response.body().getName();
                name.setText(productName);

                code.setText(productCode);

                /*START VARIANT*/

                String variantCode;

                int size = response.body().getOptions().size();
                Log.i(TAG, "onResponse: size = " + size);

                if (size != 0) {
//                    Log.i(TAG, "onResponse: options = " + response.body().getOptions().get(0).getValues().get(0).getCode());
                    variantsArray = new String[size];
                    //make sure the textView is empty
                    variants.setText("");
                    variants.append("available variants:\n");



                    for (int i = 0; i < size; i++) {
//
//                        variantsArray = new String[size];

//                        //get the variant code
                        variantCode = response.body().getOptions().get(i).getCode();
                        Log.i(TAG, "onResponse: variantCode1 = " + variantCode);

                        /*GET VALUES*/
                        String valueCode = response.body().getOptions().get(i).getValues().get(i).getCode();
                        Log.i(TAG, "onResponse: valueCode = " + valueCode);

                        variantsArray[i] = valueCode;

                        variants.append(valueCode + "\n");

                        Call<com.example.ahmed.jobtask.variants.Variants> variantsCall = apiInterface.getVariants(MainActivity.accessToken, productCode, variantCode);

                        final int finalI = i;

//                        variantsCall.enqueue(new Callback<com.example.ahmed.jobtask.variants.Variants>() {
//                            @Override
//                            public void onResponse(Call<com.example.ahmed.jobtask.variants.Variants> call, Response<com.example.ahmed.jobtask.variants.Variants> response) {
//
//                                //set the variant stock
//                                // TODO: 2/15/2018 body = null
//
//                                Log.i(TAG, "onResponse: getOnHand = " + response.body());
//                                Log.i(TAG, "onResponse: Stock = " + response);
//                                if(response.code()==404) {
//                                    variants.setText("Not found");
//                                    orderButton.setEnabled(false);
//                                }
//                                else {
//                                    productStock = response.body().getOnHand();
//
//                                    String currentStock = "available stock: " + String.valueOf(productStock);
//                                    stock.setText(currentStock);
//
//                                    //get variant name
//                                    variantName = response.body().getOptionValues().get(finalI).toString();
//
//                                    variantsArray[finalI] = variantName;
//
//                                    variants.append(finalI + 1 + "- " + variantName + "\n");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<com.example.ahmed.jobtask.variants.Variants> call, Throwable t) {
//
//                            }
//                        });

                    }

                } else {
                    variantsArray = new String[1];
                    variantsArray[0] = "Default";
                }


                /*END VARIANT*/

                //***********

            }

            @Override
            public void onFailure(Call<SingleProduct> call, Throwable t) {

            }
        });
    }
}
