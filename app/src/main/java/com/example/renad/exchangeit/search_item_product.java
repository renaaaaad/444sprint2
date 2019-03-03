package com.example.renad.exchangeit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class search_item_product extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    String id ;
    String firebase_path ;
    String item_id ;
    String new_id ;
    TextView name_item , discription_item , user,catigory_item;
    ImageView item_image ;
    Button back , request ;
    ImageView image_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item_product);
        name_item = (TextView)findViewById(R.id.item_name) ;
        discription_item = (TextView)findViewById(R.id.item_discription) ;
        item_image = (ImageView)findViewById(R.id.imageView3);
        back = (Button)findViewById(R.id.back_search);
        request = (Button)findViewById(R.id.request_serch);
        image_profile=findViewById(R.id.product_image);
        catigory_item=findViewById(R.id.category_);
user = (TextView)findViewById(R.id.username);

// to get the path of item
        final Intent intent = getIntent();
         id = intent.getStringExtra("path");
//name_item.setText(id);
// to get the item information

        ////// back btn
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(search_item_product.this, Seartch_Page.class);
                startActivity(i);
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds :dataSnapshot.getChildren()) {

                 firebase_path= ds.child("path").getValue().toString();
                    if(firebase_path.equals(id)){
                  new_id =    ds.child("productID").getValue().toString();
                       break;
                    }

                }
   FirebaseDatabase.getInstance().getReference("Products").child(new_id).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            // set the data to the layout
            name_item.setText(dataSnapshot.child("name").getValue().toString());
           discription_item.setText(dataSnapshot.child("discription").getValue().toString());
           catigory_item.setText(dataSnapshot.child("category").getValue().toString());
           String url_photo = dataSnapshot.child("path").getValue().toString();
          Glide.with(getApplicationContext()).load(url_photo).into(item_image);
//--------------------------------------------------------------------------------------------- to get the user id and profile page
         String id_user = dataSnapshot.child("useID").getValue().toString();
            FirebaseDatabase.getInstance().getReference("Users").child(id_user).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String fname=dataSnapshot.child("fname").getValue().toString();
                    String lname=dataSnapshot.child("lname").getValue().toString();

                    user.setText(fname);
                    String url_photo = dataSnapshot.child("imageurl").getValue().toString();
                    Glide.with(getApplicationContext()).load(url_photo).into(image_profile);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



//---------------------------------------------------------------------------------------------
        }// the end of the inner on datashow

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

            }// the end of the outer show

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

   // to requset prduct
            request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent1 = new Intent(getApplicationContext(),requst_page_displayProducts.class);
intent1.putExtra("product_user",new_id);
startActivity(intent1);
            }
        });


    }// on creat
}
