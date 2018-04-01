package com.example.ahmed.jobtask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.jobtask.credentials.ApiClient;
import com.example.ahmed.jobtask.credentials.ApiInterface;
import com.example.ahmed.jobtask.data.allProducts.Value;
import com.example.ahmed.jobtask.singleProduct.SingleProduct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {

    TextView name,code,variants,stock;
    Button orderButton, stockButton;
    String productCode;

    static Toast mToast;
    static int errorCode = 0;
    int productStock;
    String variantName;
    String productName;
    String[] variantsArray, variantsNames;

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
        stockButton = findViewById(R.id.stock_button);

        mToast = Toast.makeText(this, "Error: " + errorCode, Toast.LENGTH_SHORT);

        //retrieve product code to make the request
        productCode = getIntent().getStringExtra("code");

//        variantsArray = new String[100];
        /*HANDLE ORDER REQUEST*/
        orderButton.setEnabled(false);
        stockButton.setEnabled(false);
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
                    List<String> variantCodeList = new ArrayList<>();
                    List<String> variantNameList = new ArrayList<>();

//                    Log.i(TAG, "onResponse: options = " + response.body().getOptions().get(0).getValues().get(0).getCode());

                    //make sure the textView is empty
                    variants.setText("");
                    variants.append("available variants:\n");

                    for (int i = 0; i < size; i++) {
//
//                        //get the variant code
                        variantCode = response.body().getOptions().get(i).getCode();
                        Log.i(TAG, "onResponse: variantCode1 = " + variantCode);
                        int length = response.body().getOptions().get(i).getValues().size();

                        for (int j = 0; j < length; j++) {

                            Value value = response.body().getOptions().get(i).getValues().get(j);
                            /*GET VALUES*/
                            String valueCode = value.getCode();

                            Log.i(TAG, "onResponse: valueCode = " + valueCode);

                            // TODO: 2/16/2018 second loop required
                            String valueName = value.getTranslations().getEnUS().getValue();
                            Log.i(TAG, "onResponse: valueName = " + valueName);

                            variantCodeList.add(valueCode);
                            variantNameList.add(valueName);

                            Log.i(TAG, "onResponse: variantNames (i): " + i + " (j) = " + j);
//                            Log.i(TAG, "onResponse: variantsNames = " + variantsNames[i+j]);

                            variants.append((int) (i + j + 1) + " - " + variantNameList.get(i + j) + "\n");
                        }

//                        Call<com.example.ahmed.jobtask.variants.Variants> variantsCall = apiInterface.getVariants(MainActivity.accessToken, productCode, variantCode);
//
//                        final int finalI = i;

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
                    variantsNames = new String[variantNameList.size()];
                    variantsArray = new String[variantCodeList.size()];

                    for (int i = 0; i < variantCodeList.size(); i++) {
                        variantsArray[i] = variantCodeList.get(i);
                        variantsNames[i] = variantNameList.get(i);
                    }

                    Log.i(TAG, "onResponse: variantNames (size) = " + variantsNames.length);

                } else {
                    //if there is no options, get the product name
                    variantsNames = new String[1];
                    variantsArray = new String[1];
                    variantsArray[0] = response.body().getCode();
                    variantsNames[0] = response.body().getName();
                    variants.append(response.body().getName() + "\n");

                }


                /*END VARIANT*/
                orderButton.setEnabled(true);
                stockButton.setEnabled(true);

                //***********

            }

            @Override
            public void onFailure(Call<SingleProduct> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });

        if (MainActivity.admin) {
            orderButton.setVisibility(View.INVISIBLE);
            stockButton.setVisibility(View.VISIBLE);

            stockButton.setOnClickListener(new View.OnClickListener() {

                String selectedVariant = null, selectedName = null;

                @Override
                public void onClick(View view) {

                    Log.i(TAG, "onClick: patch productName = " + productName);
                    Log.i(TAG, "onClick: patch variantsNames = " + Arrays.toString(variantsNames));
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
                    builder.setTitle(productName)
                            .setSingleChoiceItems(variantsNames, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i(TAG, "onClick: NAME " + variantsNames[i]);
                                    selectedVariant = variantsArray[i];
                                    selectedName = variantsNames[i];
                                    Log.i(TAG, "onClick: selected NAME = " + selectedName);
                                    Log.i(TAG, "onClick: selected CODE = " + selectedVariant);
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(ProductDetails.this, "Edit Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    })
                            .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(ProductDetails.this, StockDialog.class);
                                    intent.putExtra("variantCode", selectedVariant);
                                    intent.putExtra("productCode", productCode);
                                    intent.putExtra("variantName", selectedName);
                                    Log.i(TAG, "onClick: variantName-details = " + selectedName);
                                    startActivity(intent);
                                }
                            }).show();

//                    Intent intent = new Intent(ProductDetails.this, StockDialog.class);
//                    intent.putExtra("variantCode", selectedVariant);
//                    startActivity(intent);
                }
            });
        } else {
            orderButton.setVisibility(View.VISIBLE);
            stockButton.setVisibility(View.INVISIBLE);
            orderButton.setOnClickListener(new View.OnClickListener() {

                String selectedVariant = null;

                @Override
                public void onClick(View view) {
                    Log.i(TAG, "onClick: " + productName);
                    Log.i(TAG, "onClick: " + Arrays.toString(variantsArray));
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
                    builder.setTitle(productName)
                            .setSingleChoiceItems(variantsArray, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i(TAG, "onClick: " + variantsArray[i]);
                                    selectedVariant = variantsArray[i];
                                    Log.i(TAG, "onClick: selected = " + selectedVariant);
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

                                    if (selectedVariant == null) {
                                        Toast.makeText(ProductDetails.this, "please select a product", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Intent intent = new Intent(ProductDetails.this, CheckOut.class);
                                    intent.putExtra("variantCode", selectedVariant);
                                    intent.putExtra("productCode", productCode);
                                    startActivity(intent);
                                }
                            }).show();
                }
            });
        }



    }
}
