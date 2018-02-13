package com.example.ahmed.jobtask;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ahmed.jobtask.data.Embedded;
import com.example.ahmed.jobtask.data.Example;
import com.example.ahmed.jobtask.data.Item;
import com.example.ahmed.jobtask.data.Links;
import com.example.ahmed.jobtask.data.Next;
import com.example.ahmed.jobtask.data.Self;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;


public class FrontEnd extends Fragment implements ProductAdapter.ItemClickListener{

    RecyclerView recyclerView;

    String[] names, codes;
    int[] ids;
    ImageView imageViews;
    Uri[] images;
    int imagesLength;
    double[] ratings;
    String Authorization;
    static ProductAdapter productAdapter;

    public static final String TAG = "items";

    /*START ONCREATE*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Authorization = "Bearer " + MainActivity.accessToken;

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final retrofit2.Call<Example> products = apiService.getAllProducts(Authorization, 200);
        products.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(retrofit2.Call<Example> call, Response<Example> response) {


                // number of total pages
                int numberOfPages = response.body().getPages();

                int size = response.body().getTotal();

                /*INITIALIZE DATA ARRAYS*/
                names = new String[size];
                ratings = new double[size];
                codes = new String[size];
                ids = new int[size];

                Links links = response.body().getLinks();

                for(int page = 0; page<numberOfPages; page++) {

                    /*GET A REFERENCE FOR CURRENT PAGE*/
                    Self self = links.getSelf();
                    Log.i(TAG, "onResponse: self = " + self.getHref());


                    //get items
                    Embedded embedded = response.body().getEmbedded();
                    List<Item> itemList = embedded.getItems();


                /*Add data to the arrays*/
                    for (int i = 0; i < itemList.size(); i++) {
                        names[i] = itemList.get(i).getName();
                        ratings[i] = itemList.get(i).getAverageRating();
                        codes[i] = itemList.get(i).getCode();
                        ids[i] = itemList.get(i).getId();
                        Log.i("data", "onResponse: code = " + codes[i] + " id = " + ids[i] + " name = " + names[i]);
                    }

                    /*MOVE TO NEXT PAGE*/
//                    String next = links.getNext().getHref();
//                    self.setHref(next);
                }


                productAdapter.setData(names, ratings);
//                recyclerView.setAdapter(productAdapter);
                Toast.makeText(getContext(), "Found " + size + " items", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(retrofit2.Call<Example> call, Throwable t) {

                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    /*END ONCREATE*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.front_fragment, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productAdapter = new ProductAdapter();
        productAdapter.setClickListener(this);

        recyclerView.setAdapter(productAdapter);

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        // TODO: 2/12/2018 open details
        Toast.makeText(view.getContext(), "Item CLicked", Toast.LENGTH_SHORT).show();
    }


}
