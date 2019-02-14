package com.example.renad.exchangeit;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<String> productnameList;
    ArrayList<String> productImgList;

    class SearchViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView path;

        public SearchViewHolder(View itemView) {
            super(itemView);
            path=(ImageView) itemView.findViewById(R.id.product_image);
            name=(TextView) itemView.findViewById(R.id.product_name);
        }
    }


    public searchAdapter(Context context, ArrayList<String> productnameList, ArrayList<String> productImgList) {
        this.context = context;
        this.productnameList =productnameList;
        this.productImgList = productImgList;
    }

    @NonNull
    @Override
    public searchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //To access Layout class
        View view= LayoutInflater.from(context).inflate(R.layout.search_list_layout,parent,false);
        return new searchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.name.setText(productnameList.get(position));
        // holder.fname.setText(fnameList.get(position));
        // final String url="http://akhbarsat.com/wp-content/uploads/2017/03/%D8%A7%D8%AB%D8%A7%D8%AB-%D8%B4%D9%82%D9%82-2016-3.jpg";
        Glide.with(context).load(productImgList.get(position)).into(holder.path);
        //Glide.with(context).load().placeholder(R.mipmap.ic_launcher_round).into(holder.path);
        //.asBitmap().placeholder(R.mipmap.ic_launcher_round).into(holder.path);
       // holder.fname.setText(fnameList.get(position));
       // final String url="http://akhbarsat.com/wp-content/uploads/2017/03/%D8%A7%D8%AB%D8%A7%D8%AB-%D8%B4%D9%82%D9%82-2016-3.jpg";
          Glide.with(context).load(productImgList.get(position)).into(holder.path);
        //Glide.with(context).load().placeholder(R.mipmap.ic_launcher_round).into(holder.path);
               //.asBitmap().placeholder(R.mipmap.ic_launcher_round).into(holder.path);
    }


    @Override
    public int getItemCount() {
        return productnameList.size();
    }
}