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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FrontEnd extends Fragment implements ProductAdapter.ItemClickListener{

    public static final String TAG = "items";
    static ProductAdapter productAdapter;
    RecyclerView recyclerView;
    int page = 1;
    String[] names, codes;
    Integer[] ids;
    ImageView imageViews;
    Uri[] images;
    int imagesLength;
    Double[] ratings;
    String Authorization;
    ArrayList<String> nameList;
    ArrayList<String> codeList;
    ArrayList<Integer> idList;
    ArrayList<Double> ratingList;
    int sizeOfProducts = 0;
    int numberOfPages;

    /*START ONCREATE*/
    ApiInterface apiService;
    retrofit2.Call<Example> products;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Authorization = "Bearer " + MainActivity.accessToken;

        apiService = ApiClient.getClient().create(ApiInterface.class);

        nameList = new ArrayList<>();
        codeList = new ArrayList<>();
        idList = new ArrayList<>();
        ratingList = new ArrayList<>();

        loadFirstPage();
        while (page <= numberOfPages) {
            loadNextPage();
        }

        /*INITIALIZE ARRAYS*/
        names = new String[sizeOfProducts];
        ratings = new Double[sizeOfProducts];
        codes = new String[sizeOfProducts];
        ids = new Integer[sizeOfProducts];

        names = nameList.toArray(new String[nameList.size()]);
        ratings = ratingList.toArray(new Double[ratingList.size()]);
        codes = codeList.toArray(new String[codeList.size()]);
        ids = idList.toArray(new Integer[idList.size()]);

        productAdapter.setData(names, ratings);
//                recyclerView.setAdapter(productAdapter);
        Toast.makeText(getContext(), "Found " + sizeOfProducts + " items", Toast.LENGTH_SHORT).show();
    }

    private void loadFirstPage() {

        products = apiService.getAllProducts(Authorization, page++);

        products.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(retrofit2.Call<Example> call, Response<Example> response) {


                // number of total pages
//                numberOfPages = response.body().getPages();


                int size = response.body().getTotal();

                sizeOfProducts += size;

//                Links links = response.body().getLinks();


//                    /*GET A REFERENCE FOR CURRENT PAGE*/
//                    Self self = links.getSelf();
//                    Log.i(TAG, "onResponse: self = " + self.getHref());
                Embedded embedded;
                List<Item> itemList;

                //get items
                embedded = response.body().getEmbedded();

                itemList = embedded.getItems();


                /*Add data to the arrays*/
                for (int i = 0; i < itemList.size(); i++) {
                    nameList.add(itemList.get(i).getName());
                    ratingList.add(itemList.get(i).getAverageRating());
                    codeList.add(itemList.get(i).getCode());
                    idList.add(itemList.get(i).getId());
//                        Log.i("data", "onResponse: code = " + codes[i] + " id = " + ids[i] + " name = " + names[i]);
                }

//                    if (page != numberOfPages) {
//                        loadNextPage();
//                    }

                    /*MOVE TO NEXT PAGE*/
//                    String next = links.getNext().getHref();
//                    self.setHref(next);

            }

            @Override
            public void onFailure(retrofit2.Call<Example> call, Throwable t) {

                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void loadNextPage() {

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final retrofit2.Call<Example> products = apiService.getAllProducts(Authorization, page++);
        products.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {


                // number of total pages
//                numberOfPages = response.body().getPages();


                int size = response.body().getTotal();

                sizeOfProducts += size;

                /*INITIALIZE DATA ARRAYS*/


//                Links links = response.body().getLinks();


//                    /*GET A REFERENCE FOR CURRENT PAGE*/
//                    Self self = links.getSelf();
//                    Log.i(TAG, "onResponse: self = " + self.getHref());
                Embedded embedded;
                List<Item> itemList;

                //get items
                embedded = response.body().getEmbedded();

                itemList = embedded.getItems();


                /*Add data to the arrays*/
                for (int i = 0; i < itemList.size(); i++) {
                    nameList.add(itemList.get(i).getName());
                    ratingList.add(itemList.get(i).getAverageRating());
                    codeList.add(itemList.get(i).getCode());
                    idList.add(itemList.get(i).getId());
//                    Log.i("data", "onResponse: code = " + codes[i] + " id = " + ids[i] + " name = " + names[i]);
                }

//                    if (page != numberOfPages) {
//                        loadNextPage();
//                    }

                    /*MOVE TO NEXT PAGE*/
//                    String next = links.getNext().getHref();
//                    self.setHref(next);

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

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
