package com.example.renad.exchangeit;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.MainActivity_profilePage;
import com.example.renad.exchangeit.R;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class sitting extends AppCompatActivity {
    private static final  int RESULT_LOAD_IMAGE=1;
    private Button cancle , update ;
    private EditText name , lname ,phone;
    Spinner location ;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private StorageReference mStorageRef;
    ImageView imageView_user ;
    private Uri selectedimage;
    String user_id;
     String url_photo;
    String Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting);
        cancle = (Button) findViewById(R.id.Cancel);
        update = (Button) findViewById(R.id.Change);
        name = (EditText) findViewById(R.id.Fname);
        lname = (EditText) findViewById(R.id.Lname);
        location = (Spinner) findViewById(R.id.location);

        phone = (EditText) findViewById(R.id.phoneNumber);
imageView_user = (ImageView)findViewById(R.id.imageView2) ;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
// please work 
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

//location.set(dataSnapshot.child("city").getValue().toString());
                lname.setText(dataSnapshot.child("lname").getValue().toString());
                phone.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                 url_photo = dataSnapshot.child("imageurl").getValue().toString();
                Glide.with(getApplicationContext()).load(url_photo).into(imageView_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

imageView_user.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent galleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
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



                if(!phone.getText().toString().equals("")){
                    Phone= phone.getText().toString();



                }
                else {
                    phone.setError("Please Enter Your Phone Number  ");
                    phone.requestFocus();
                    return;

                }


                if(Phone.length()<10 || Phone.length()>10){
                    phone.setError("Please Enter Your Phone Number Correctly ");
                    phone.requestFocus();
                    return;
                }


//                if(city.getText().toString().equals("")){
//                    city.setError("Please Don't leave this Field Empty ");
//                    city.findFocus();
//                    return;
//                }

                if(phone.getText().toString().equals("")){
                    phone.setError("Please Don't leave this Field Empty ");
                    phone.findFocus();
                    return;
                }

                final String Uname = name.getText().toString();
                final String Ulname = lname.getText().toString();
//                final String Ulocation = city.getText().toString();
                final String Uphone = phone.getText().toString();
                final String Ucity = location.getSelectedItem().toString();




                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("fname").setValue(Uname);
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("lname").setValue(Ulname);
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("city").setValue(Ucity);
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("phoneNumber").setValue(Uphone);
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("imageurl").setValue(url_photo);


                startActivity(new Intent(getApplicationContext(),MainActivity_profilePage.class));




            }
        });

    }//on create
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data != null ){
            selectedimage=data.getData();
           // itemimage.setImageURI(selectedimage);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReference("images/Users/profile/"+user_id+"/profile.png");
            Task<Uri> urlTask = storageRef.putFile(selectedimage).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageRef.getDownloadUrl();
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                  String  path = uri.toString();
                    url_photo = path;
                    FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("imageurl").setValue(path);
                    Glide.with(getApplicationContext()).load(url_photo).into(imageView_user);


                }
            });











        }// big if 2





    }

}