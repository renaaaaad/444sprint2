package com.example.renad.exchangeit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.renad.exchangeit.Adapter.Adapter_listProduct_request;
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

public class requst_page_displayProducts extends AppCompatActivity {

    RecyclerView recyclerView ;
    MyFotoAdapter myFotoAdapter;
    Adapter_listProduct_request adapter_listProduct_request ;
    List<Product> productList;
String user_ID ;
    FirebaseUser firebaseUser;
    TextView backbtn;

    private myPHotoAdapter myPHotoAdapter ;
    private List<Product> list_product ;
String user_ID_exchanged;
String user_product ;
    //------------------------------------------------ the data base varible
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requst_page_display_products);

        FirebaseUser userID=FirebaseAuth.getInstance().getCurrentUser();
        user_ID = userID.getUid().toString();
        SharedPreferences perfs=getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
Intent intent = getIntent();
        user_ID_exchanged = intent.getStringExtra("product_user");
        user_product = intent.getStringExtra("product_name");
        backbtn = (TextView) findViewById(R.id.back11);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requst_page_displayProducts.this, MainActivity_profilePage.class);
                startActivity(i);
            }
        });

        //show product

        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1= new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager1);
        productList=new ArrayList<>();
        adapter_listProduct_request=new Adapter_listProduct_request(getApplicationContext(),productList);
        adapter_listProduct_request.setCurrent_product(user_product);
        adapter_listProduct_request.setUser_ex(user_ID_exchanged);
        recyclerView.setAdapter(adapter_listProduct_request);

        myFotos(user_ID);

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
                adapter_listProduct_request.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
