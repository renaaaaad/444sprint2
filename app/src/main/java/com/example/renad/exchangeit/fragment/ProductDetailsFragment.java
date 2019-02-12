package com.example.renad.exchangeit.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renad.exchangeit.Adapter.ProductAdapter;
import com.example.renad.exchangeit.Product;
import com.example.renad.exchangeit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailsFragment extends Fragment {

    String postid;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    List<Product> productList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_product_details, container, false);

        SharedPreferences preferences= getContext().getSharedPreferences("PREFS",Context.MODE_PRIVATE);

        postid=preferences.getString("postid","none");

        recyclerView =view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        productList= new ArrayList<>();
        productAdapter= new ProductAdapter(getContext(),productList);

        recyclerView.setAdapter(productAdapter);


        readPost();

        return view;
    }

    private void readPost() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Products").child("postid");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                productList.clear();
                Product product =dataSnapshot.getValue(Product.class);
                productList.add(product);

                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
