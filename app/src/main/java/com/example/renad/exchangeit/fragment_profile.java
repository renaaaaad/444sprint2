package com.example.renad.exchangeit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.renad.exchangeit.Adapter.MySearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class fragment_profile extends Fragment {

    TextView searchView;
    RecyclerView recyclerView;
    MySearchAdapter mySearchAdapter;
    List<SystemProduct> productList;
String userid ;
    TextView textView;
    public fragment_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);

//----------------------------------------------
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
         userid=user.getUid();



        searchView=(TextView)view.findViewById(R.id.searchForProduct);
        searchView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(),Seartch_Page.class));
            }
        });
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle_product);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1= new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager1);
        productList=new ArrayList<>();
        mySearchAdapter =new MySearchAdapter(getContext(),productList);
        recyclerView.setAdapter(mySearchAdapter);
        textView=(TextView)view.findViewById(R.id.searchForProduct);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0);


        myFotos();
return view;


    }
    private void myFotos() {

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Products");
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SystemProduct product = snapshot.getValue(SystemProduct.class);
                    ///user id does not equals userid
                    String idProduct = product.getUseID();
                    if((idProduct.equals(userid))){
                        continue;}
                    else {

                    productList.add(product);}

                }

                Collections.reverse(productList);
                mySearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
