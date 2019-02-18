package com.example.renad.exchangeit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.Adapter.MyFotoAdapter;
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


public class fragment_seartch extends Fragment {

    private BottomNavigationView navigation;
    private TextView name_text , city2;
    private String name_from_firebase;
    private String user_id;
    private String user_name;
    Button cancel ;

    RecyclerView recyclerView ;
    private final int NUM_RE_COLUMNS = 3;
    MyFotoAdapter myFotoAdapter;
    List<Product> productList;

    /////Nora
    ImageView image_profile;

    FirebaseUser firebaseUser;

    String profileid;

    ////

    private myPHotoAdapter myPHotoAdapter ;
    private List<Product> list_product ;

    //------------------------------------------------ the data base varible
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_fragment_seartch, container, false);

        name_text = (TextView)view.findViewById(R.id.name);
        city2 =        (TextView)view.findViewById(R.id.city);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences perfs=getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid=perfs.getString("profileid","none");

        image_profile=view.findViewById(R.id.imageProfile);


        String userid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name_text.setText(dataSnapshot.child("fname").getValue().toString());
                city2.setText(dataSnapshot.child("city").getValue().toString());
                String url_photo = dataSnapshot.child("imageurl").getValue().toString();
                Glide.with(getContext()).load(url_photo).into(image_profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //show product

        recyclerView=(RecyclerView)view.findViewById(R.id.recycle);
//        int recyclewidth=getResources().getDisplayMetrics().widthPixels;
//        int imagewidth=recyclewidth/NUM_RE_COLUMNS;
//        recyclerView.setMinimumWidth(imagewidth);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1= new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager1);
        productList=new ArrayList<>();
        myFotoAdapter=new MyFotoAdapter(getContext(),productList);
        recyclerView.setAdapter(myFotoAdapter);

        myFotos(userid);

//        myProducts(userid);





        navigation = (BottomNavigationView) view.findViewById(R.id.bottomNavigationView);
        cancel  = (Button)view.findViewById(R.id.button6);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),loginPage.class));
            }
        });
        return view;
    }//oncreat

    private void showdata(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds :dataSnapshot.getChildren()){
            User user_new = new User();
            user_new.setFname(ds.child(user_id).getValue(User.class).getFname());
            user_new.setLname(ds.child(user_id).getValue(User.class).getLname());
            user_new.setCity(ds.child(user_id).getValue(User.class).getCity());
            user_new.setFname(ds.child(user_id).getValue(User.class).getFname());
            user_new.setEmail(ds.child(user_id).getValue(User.class).getEmail());
            user_new.setPhoneNumber(ds.child(user_id).getValue(User.class).getPhoneNumber());
            user_new.setId(user_id);
            name_text.setText(user_new.getFname());
        }//for part


    }



    public void Add(View view) {
        Intent intent2 = new Intent(getContext(),addProduct.class);
        intent2.putExtra("User_Id",user_id);
        startActivity(intent2);
    }



    private void myFotos(String id){

        DatabaseReference reference3=FirebaseDatabase.getInstance().getReference("Users");
        reference3.child(id).child("Products").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product=snapshot.getValue(Product.class);
                    productList.add(product);

                }

                Collections.reverse(productList);
                myFotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





}
