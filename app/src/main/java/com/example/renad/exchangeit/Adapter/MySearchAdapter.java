package com.example.renad.exchangeit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.Product;
import com.example.renad.exchangeit.R;

import java.util.List;

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.ViewHolder>{
    private Context context;
    private List<Product> mProduct;


    public MySearchAdapter(Context context, List<Product> mProduct) {
        this.context = context;
        this.mProduct = mProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.view_prodect_s,viewGroup,false);
        return new MySearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Product product=mProduct.get(i);

        Glide.with(context).load(product.getPath()).into(viewHolder.search_image);

    }

    @Override
    public int getItemCount() {

        return mProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView search_image;

        public ViewHolder(View itemView) {
            super(itemView);

            search_image = itemView.findViewById(R.id.search_image);
        }
    }
}
