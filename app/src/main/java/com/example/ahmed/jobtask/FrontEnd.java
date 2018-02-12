package com.example.ahmed.jobtask;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Ahmed on 2/12/2018.
 */

public class FrontEnd extends Fragment implements ProductAdapter.ItemClickListener{

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    String[] titles;
    ImageView imageViews;
    Uri[] images;
    int imagesLength;
    double[] ratings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.front_fragment, null);

        recyclerView = container.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        productAdapter = new ProductAdapter();
        productAdapter.setClickListener(this);
        // TODO: 2/12/2018 request retrofit then set data and the adapter

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        // TODO: 2/12/2018 open details
        Toast.makeText(view.getContext(), "Item CLicked", Toast.LENGTH_SHORT).show();
    }
}
