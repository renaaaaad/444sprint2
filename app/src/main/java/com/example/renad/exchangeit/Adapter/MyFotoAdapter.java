package com.example.renad.exchangeit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
//import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.Product;
import com.example.renad.exchangeit.R;
import com.example.renad.exchangeit.SquareimageView;
import com.example.renad.exchangeit.SystemProduct;
import com.example.renad.exchangeit.fragment.ProductDetailsFragment;
import com.example.renad.exchangeit.itemProduct;

import java.util.List;

public class MyFotoAdapter extends RecyclerView.Adapter<MyFotoAdapter.ViewHolder>{

    private Context context;
    private List<Product> mPosts;
    SystemProduct systemProduct;


private String id_image;


    public MyFotoAdapter(Context context, List<Product> mPosts) {
        this.context = context;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.fotos_item,viewGroup,false);
        return new MyFotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Product product = mPosts.get(i);
        id_image = product.getName();

        Glide.with(context).load(product.getPath()).into(viewHolder.post_image);


        /////////
//        viewHolder.post_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                SharedPreferences.Editor editor=context.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
//                editor.putString("postid",systemProduct.getProductID());
//                editor.apply();
//
//
//
//                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ProductDetailsFragment()).commit();
//
//            }
//        });
        //////////

    }

    @Override
    public int getItemCount() {
        return mPosts.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public SquareimageView post_image;



        public ViewHolder(final View itemView) {
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);



            post_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    Product product2 = mPosts.get(position);
                    String dec=product2.getProduct_number();
                    String itemID=product2.getPath();

                    String  url_photo=product2.getPath();

                    Intent intent = new Intent(context, itemProduct.class);
//                    intent.putExtra("productId",dec);

                    Bundle extras = new Bundle();
                    extras.putString("productId",dec);
                    extras.putString("ItemId", itemID);
                    intent.putExtras(extras);

                    context.startActivity(intent);






                }
            });
        }
    }
}
