package com.example.renad.exchangeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class itemProduct extends AppCompatActivity {
FirebaseStorage firebaseStorage ;
FirebaseAuth auth ;


    private EditText item_description;
    private EditText item_name;
    ImageView imageView_item ;
private String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_item);
imageView_item = findViewById(R.id.image_container);
item_description=(EditText) findViewById(R.id.description);
        item_name=(EditText) findViewById(R.id.itemname);





// reffrence to the database

        FirebaseUser user=auth.getInstance().getCurrentUser();
        String userid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

// to get the data and out it in the layout

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Intent intent = getIntent();
                id = intent.getStringExtra("productId");

              item_name.setText(dataSnapshot.child("Products").child(id).child("name").getValue().toString());
              item_description.setText(dataSnapshot.child("Products").child(id).child("discription").getValue().toString());
                String url_photo = dataSnapshot.child("Products").child(id).child("path").getValue().toString();
                Glide.with(getApplicationContext()).load(url_photo).into(imageView_item);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }//on create
}

