package com.example.renad.exchangeit;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class reviewMyRejectRequests extends AppCompatActivity {

    String rec_user , rec_prod , int_prod , int_user , id3 ;
    ImageView prduct1 , prduct2 , profile;
    TextView name , productName , productDis;
    Button cancelbtn;
    TextView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_my_reject_requests);

        prduct1 = (ImageView)findViewById(R.id.myPrduct) ;
        prduct2 = (ImageView)findViewById(R.id.exchange) ;
        profile = (ImageView)findViewById(R.id.product_image) ;
        name = (TextView)findViewById(R.id.user_name) ;
        productName = (TextView)findViewById(R.id.product_name) ;
        productDis = (TextView)findViewById(R.id.poduct_des) ;

        backbtn=(TextView) findViewById(R.id.back11);





        Intent intent =getIntent();

        id3 = intent.getStringExtra("id");
        name.setText(intent.getStringExtra("pro_user"));
        productName.setText(intent.getStringExtra("pro_name"));
        productDis.setText(intent.getStringExtra("pro_des"));
        Glide.with(getApplicationContext()).load(intent.getStringExtra("pro_recive")).into(prduct1);
        Glide.with(getApplicationContext()).load(intent.getStringExtra("pro_intiate")).into(prduct2);


        //---------

        rec_prod = intent.getStringExtra("rec_prod");
        rec_user = intent.getStringExtra("rec_user");
        int_prod = intent.getStringExtra("int_prod");
        int_user = intent.getStringExtra("int_user");
        String id = intent.getStringExtra("id");
        //-----------

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(id3).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                Glide.with(getApplicationContext()).load(dataSnapshot.child("imageurl").getValue().toString()).into(profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity_profilePage.class));

            }
        });


    }
}

