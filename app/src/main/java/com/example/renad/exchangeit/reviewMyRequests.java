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

public class reviewMyRequests extends AppCompatActivity {

    String rec_user , rec_prod , int_prod , int_user , id3 ;
    ImageView prduct1 , prduct2 , profile;
    TextView name , productName , productDis;
    Button cancelbtn;
    TextView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_my_requests);

        prduct1 = (ImageView)findViewById(R.id.myPrduct) ;
        prduct2 = (ImageView)findViewById(R.id.exchange) ;
        profile = (ImageView)findViewById(R.id.product_image) ;
        name = (TextView)findViewById(R.id.user_name) ;
        productName = (TextView)findViewById(R.id.product_name) ;
        productDis = (TextView)findViewById(R.id.poduct_des) ;
        cancelbtn=(Button)findViewById(R.id.cancel);
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
        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if( dataSnapshot.child("imageurl").getValue()==null) return;
               Glide.with(getApplicationContext()).load(dataSnapshot.child("imageurl").getValue().toString()).into(profile);

              }

                 @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                         });




                                cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
                                FirebaseDatabase.getInstance().getReference("Users").child(int_user).child("Initiate_requests").child(ii).child("status").setValue("Cancel");
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
                                FirebaseDatabase.getInstance().getReference("Users").child(rec_user).child("requestsReceive").child(ii).child("status").setValue("Cancel");
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

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity_profilePage.class));

            }
        });


    }
}
