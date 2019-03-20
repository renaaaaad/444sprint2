package com.example.renad.exchangeit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
private Button Confirm_button , cancel;
ImageView myProduct , exchange_product , userr;
String userid ;
String eID ;
TextView textView ,back  ;
    TextView backbtn;

String p_number ;
String intiate_path , recive_path , intiate_name , p_name2 , p_des2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compleate_requests);




        userr = (ImageView)findViewById(R.id.product_image) ;
        myProduct = (ImageView)findViewById(R.id.myPrduct) ;
        exchange_product = (ImageView)findViewById(R.id.exchange) ;
textView = (TextView)findViewById(R.id.product_name) ;
        cancel = (Button)findViewById(R.id.homePage) ;
        backbtn = (TextView) findViewById(R.id.back11);

         // fill the images
        Intent intent = getIntent();
final String pName = intent.getStringExtra("productId"); // for the user current product

final String user_exchange = intent.getStringExtra("userToExchangeWith"); // the user I want to exchange with

final  String user_Exchange_product = intent.getStringExtra("exchane_product") ; // to get the product I want to exchange with  ;

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(compleate_requests.this, MainActivity_profilePage.class);
                startActivity(i);
            }
        });

        // get the user
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences perfs=getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
         userid=user.getUid();

        DatabaseReference reference12 = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference12.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                intiate_name = dataSnapshot.child("fname").getValue().toString()+" "+dataSnapshot.child("lname").getValue().toString() ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



         //--------------
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid).child("Products");
        reference.child(pName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               String url_photo = dataSnapshot.child("path").getValue().toString();
                intiate_path = url_photo ;
            Glide.with(getApplicationContext()).load(url_photo).into(myProduct);
                p_name2 = dataSnapshot.child("name").getValue().toString();
                p_des2 = dataSnapshot.child("discription").getValue().toString() ;
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

                                recive_path = url_photo;

                                DatabaseReference reference10 = FirebaseDatabase.getInstance().getReference("Users").child(eID);
                                reference10.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String url_photo2 = dataSnapshot.child("imageurl").getValue().toString();
                                        Glide.with(getApplicationContext()).load(url_photo2).into(userr);

                                        String nname = dataSnapshot.child("fname").getValue().toString();
                                        String lnname = dataSnapshot.child("lname").getValue().toString();
                                        textView.setText(nname+" "+lnname);

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
        DatabaseReference reference7 = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
// to the first user
                String requestString  = dataSnapshot.child("requests").getValue().toString();
                int requestInt = Integer.parseInt(requestString);
                requestInt++;
                String requestString2  = Integer.toString(requestInt);
                FirebaseDatabase.getInstance().getReference("Users").child(userid).child("requests").setValue(requestString2);
                requestProductDetails requestProductDetails = new requestProductDetails(intiate_path,recive_path,intiate_name,p_name2,p_des2);
  user_Requests user_requests = new user_Requests(requestInt,userid,eID,pName,p_number,"Waiting",requestProductDetails) ;
                FirebaseDatabase.getInstance().getReference("Users").child(userid).child("Initiate_requests").child(requestString2).setValue(user_requests);
// for the second user

 DatabaseReference reference8  = FirebaseDatabase.getInstance().getReference("Users").child(eID);

reference8.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String requestString2  = dataSnapshot.child("recive_request").getValue().toString();
        int requestInt2 = Integer.parseInt(requestString2);
        requestInt2++;
        String requestString3  = Integer.toString(requestInt2);
        FirebaseDatabase.getInstance().getReference("Users").child(eID).child("Receive_requestsNum").setValue(requestString3);
        requestProductDetails requestProductDetails2 = new requestProductDetails(intiate_path,recive_path,intiate_name,p_name2,p_des2);

        user_Requests user_requests = new user_Requests(requestInt2,userid,eID,pName,p_number,"Waiting",requestProductDetails2) ;
        FirebaseDatabase.getInstance().getReference("Users").child(eID).child("requestsReceive").child(requestString3).setValue(user_requests);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
Intent intent2 = new Intent(getApplicationContext(),MainActivity_profilePage.class);
startActivity(intent2);

            }//on change data

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
});


cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent3 = new Intent(getApplicationContext(),MainActivity_profilePage.class);
        startActivity(intent3);
    }
});



    } // on creat
} // class
