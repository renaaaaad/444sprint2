package com.example.renad.exchangeit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class compleate_requests extends AppCompatActivity {
private Button Confirm_button ;
ImageView myProduct , exchange_product ;
String userid ;
String eID ;
TextView textView ;
String p_number ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compleate_requests);

        myProduct = (ImageView)findViewById(R.id.myPrduct) ;
        exchange_product = (ImageView)findViewById(R.id.exchange) ;
textView = (TextView)findViewById(R.id.product_name) ;

         // fill the images
        Intent intent = getIntent();
final String pName = intent.getStringExtra("productId"); // for the user current product

final String user_exchange = intent.getStringExtra("userToExchangeWith"); // the user I want to exchange with

final  String user_Exchange_product = intent.getStringExtra("exchane_product") ; // to get the product I want to exchange with  ;



        // get the user
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences perfs=getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
         userid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid).child("Products");
        reference.child(pName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               String url_photo = dataSnapshot.child("path").getValue().toString();
            Glide.with(getApplicationContext()).load(url_photo).into(myProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//-----------------------------------------------


        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Products").child(user_exchange);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eID = dataSnapshot.child("useID").getValue().toString();


     DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Users").child(eID).child("Products");

                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds :dataSnapshot.getChildren()) {

                            if((ds.child("name").getValue().toString()).equals(user_Exchange_product)){
                                p_number = ds.child("product_number").getValue().toString();
                                break;
                            }

                        }

                        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("Users").child(eID).child("Products");
                        reference4.child(p_number).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String url_photo = dataSnapshot.child("path").getValue().toString();
                                Glide.with(getApplicationContext()).load(url_photo).into(exchange_product);

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





// to get the the confirmation
Confirm_button = (Button)findViewById(R.id.confirm);

Confirm_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
// get the user of the product



    }
});




    } // on creat
} // class
