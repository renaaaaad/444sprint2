package com.example.renad.exchangeit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class requestDetaile extends AppCompatActivity {
String rec_user , rec_prod , int_prod , int_user ;
ImageView prduct1 , prduct2 , profile;
TextView name , productName , productDis;
Button accept , reject ;
String pname2 , pname22;
String id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detaile);

        prduct1 = (ImageView)findViewById(R.id.myPrduct) ;
        prduct2 = (ImageView)findViewById(R.id.exchange) ;
        profile = (ImageView)findViewById(R.id.product_image) ;
        name = (TextView)findViewById(R.id.user_name) ;
        productName = (TextView)findViewById(R.id.product_name) ;
        productDis = (TextView)findViewById(R.id.poduct_des) ;
        accept = (Button)findViewById(R.id.accept);
        reject = (Button) findViewById(R.id.reject);



        android.support.v7.widget.Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Go Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        Intent intent = getIntent() ;
        rec_prod = intent.getStringExtra("rec_prod");
        rec_user = intent.getStringExtra("rec_user");
        int_prod = intent.getStringExtra("int_prod");
        int_user = intent.getStringExtra("int_user");
        id = intent.getStringExtra("id");


        // firebase to get the user

        SharedPreferences perfs=getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(rec_user).child("Products");
        reference.child(rec_prod).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String url_photo = dataSnapshot.child("path").getValue().toString();
                Glide.with(getApplicationContext()).load(url_photo).into(prduct1);
                pname22 = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // finish getting the user

        // getting the other user image and data

        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("Users").child(int_user).child("Products");
                        reference4.child(int_prod).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String url_photo = dataSnapshot.child("path").getValue().toString();
                                Glide.with(getApplicationContext()).load(url_photo).into(prduct2);
                                String nname = dataSnapshot.child("name").getValue().toString();
                                String des = dataSnapshot.child("discription").getValue().toString();
                                productName.setText(nname);
                                productDis.setText(des);
                                pname2 = nname ;

                                DatabaseReference reference10 = FirebaseDatabase.getInstance().getReference("Users").child(int_user);
                                reference10.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String url_photo2 = dataSnapshot.child("imageurl").getValue().toString();
                                        Glide.with(getApplicationContext()).load(url_photo2).into(profile);
                                        String nname = dataSnapshot.child("fname").getValue().toString();
                                        String lnname = dataSnapshot.child("lname").getValue().toString();
                                        name.setText(nname+" "+lnname);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





                // String url_photo = dataSnapshot.child("path").getValue().toString();
                // Glide.with(getApplicationContext()).load(url_photo).into(myProduct);


        // finish getting the other user information
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete from the init user
                DatabaseReference reference5 = FirebaseDatabase.getInstance().getReference("Users").child(int_user).child("Products");
                reference5.child(int_prod).removeValue();

                // delete from the rec user
                DatabaseReference reference6 = FirebaseDatabase.getInstance().getReference("Users").child(rec_user).child("Products");
                reference6.child(rec_prod).removeValue();

                //delete from the products
           FirebaseDatabase.getInstance().getReference("Products").child(pname22+rec_user).removeValue();
                FirebaseDatabase.getInstance().getReference("Products").child(pname2+int_user).removeValue();

                // ----------------------------------------------------------------------------------


//FirebaseDatabase.getInstance().getReference("Users").child(int_prod).child("Initiate_requests").child(id).setValue("Accepted");
//----------------------------------------------------------------------------------------------------------------------
                DatabaseReference reference10 = FirebaseDatabase.getInstance().getReference("Users").child(int_user).child("Initiate_requests");
                reference10.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String int_prod2 = ds.child("initial_product").getValue().toString();
                            String int_user2 = ds.child("initial_user").getValue().toString();
                            String rec_user2 = ds.child("recive_user").getValue().toString();
                            String rec_prod2 = ds.child("recive_product").getValue().toString();
                            String ii =  ds.child("id").getValue().toString();

                            if((int_prod2.equals(int_prod))&&(int_user2.equals(int_user))&&(rec_user2.equals(rec_user))&&(rec_prod2.equals(rec_prod))){
                                FirebaseDatabase.getInstance().getReference("Users").child(int_user).child("Initiate_requests").child(ii).child("status").setValue("Accepted");
                                return;
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//-----------------------------------------------------------------------------------------------------------------------
DatabaseReference reference9 = FirebaseDatabase.getInstance().getReference("Users").child(rec_user).child("requestsReceive");
reference9.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
         String int_prod2 = ds.child("initial_product").getValue().toString();
         String int_user2 = ds.child("initial_user").getValue().toString();
         String rec_user2 = ds.child("recive_user").getValue().toString();
         String rec_prod2 = ds.child("recive_product").getValue().toString();
            String ii =  ds.child("id").getValue().toString();

         if((int_prod2.equals(int_prod))&&(int_user2.equals(int_user))&&(rec_user2.equals(rec_user))&&(rec_prod2.equals(rec_prod))){
             FirebaseDatabase.getInstance().getReference("Users").child(rec_user).child("requestsReceive").child(ii).child("status").setValue("Accepted");
return;
         }

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

startActivity(new Intent(getApplicationContext(),MainActivity_profilePage.class));
            }// on click
        });


    }// on create


}// class
