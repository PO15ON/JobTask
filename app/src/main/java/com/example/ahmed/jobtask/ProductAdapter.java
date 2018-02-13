package com.example.ahmed.jobtask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ahmed on 2/12/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private static final String TAG = "ProductAdapter";
    private String[] titles;
    private String[] imageArray;
    private double[] ratings;
    private ItemClickListener mClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = titles[position];
        double rating = ratings[position];
//        Uri image = Uri.parse(imageArray[position]);
//        Log.i(TAG, "onBindViewHolder: image = " + image);
//        Log.i(TAG, "onBindViewHolder: name = " + title);

        holder.title.setText(title);
        holder.rating.setText(Double.toString(rating));

//        Picasso.with(holder.imageView.getContext()).load(image).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (null == titles || null == ratings) return 0;
        return titles.length;
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setData(String[] newTitles, double[] newRatings) {

//        imageArray = newImgUrls;
        titles = newTitles;
        ratings = newRatings;

        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
        
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        final ImageView imageView;
        final TextView title, rating;

        public ViewHolder(View itemView) {
            super(itemView);

//            imageView = itemView.findViewById(R.id.image_view);
            title = itemView.findViewById(R.id.product_title);
            rating = itemView.findViewById(R.id.rating);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

}
