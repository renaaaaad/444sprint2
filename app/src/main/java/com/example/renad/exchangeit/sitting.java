package com.example.renad.exchangeit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.MainActivity_profilePage;
import com.example.renad.exchangeit.R;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class sitting extends AppCompatActivity {
    private Button cancle , update ;
    private EditText name , lname ,phone , location ;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private StorageReference mStorageRef;
    ImageView imageView_user ;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting);
        cancle = (Button) findViewById(R.id.Cancel);
        update = (Button) findViewById(R.id.Change);
        name = (EditText) findViewById(R.id.Fname);
        lname = (EditText) findViewById(R.id.Lname);
        location = (EditText) findViewById(R.id.location);
        phone = (EditText) findViewById(R.id.phoneNumber);
imageView_user = (ImageView)findViewById(R.id.imageView2) ;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(firebaseUser.getUid());
            user_id = firebaseUser.getUid();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("fname").getValue().toString());
                location.setText(dataSnapshot.child("city").getValue().toString());
                lname.setText(dataSnapshot.child("lname").getValue().toString());
                phone.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                String url_photo = dataSnapshot.child("image").getValue().toString();
                Glide.with(getApplicationContext()).load(url_photo).into(imageView_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity_profilePage.class));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().equals("")){
                    name.setError("Please Don't leave this Field Empty ");
                    name.findFocus();
                    return;
                }

                if(lname.getText().toString().equals("")){
                    lname.setError("Please Don't leave this Field Empty ");
                    lname.findFocus();
                    return;
                }

                if(location.getText().toString().equals("")){
                    location.setError("Please Don't leave this Field Empty ");
                    location.findFocus();
                    return;
                }

                if(phone.getText().toString().equals("")){
                    phone.setError("Please Don't leave this Field Empty ");
                    phone.findFocus();
                    return;
                }

                final String Uname = name.getText().toString();
                final String Ulname = lname.getText().toString();
                final String Ulocation = location.getText().toString();
                final String Uphone = phone.getText().toString();




                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("fname").setValue(Uname);
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("lname").setValue(Ulname);
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("city").setValue(Ulocation);
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("phoneNumber").setValue(Uphone);


                startActivity(new Intent(getApplicationContext(),MainActivity_profilePage.class));




            }
        });

    }//on create

}