package com.example.renad.exchangeit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

public class itemProduct extends AppCompatActivity {


    FirebaseStorage firebaseStorage ;
    FirebaseAuth auth ;
       private DatabaseReference firebaseDatabase;
        private FirebaseAuth firebaseAuth;
        private FirebaseAuth.AuthStateListener authStateListener;
    private EditText item_description;
    private EditText item_name;
    ImageView imageView_item ;
private String id ;
private Button CanceleButton;
private Button changebtn;
private Button delebtn;

    private Uri selectedimage;
    String user_id;
    String url_photo;
    private static final  int RESULT_LOAD_IMAGE=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_item);

        Intent intent2 = getIntent();
        final String Itemid = intent2.getStringExtra("ItemId");


        imageView_item = findViewById(R.id.image_container);
        item_description = (EditText) findViewById(R.id.description);
        item_name = (EditText) findViewById(R.id.itemname);
        CanceleButton = (Button) findViewById(R.id.cancelbut);
        changebtn = (Button) findViewById(R.id.changebut);
        delebtn = (Button) findViewById(R.id.deletebut);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
// please work
        if (firebaseUser != null) {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(firebaseUser.getUid());
            user_id = firebaseUser.getUid();
        }


//        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Products");
//        reference2.child(Itemid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                item_name.setText(dataSnapshot.child("name").getValue().toString());
//                item_description.setText(dataSnapshot.child("discription").getValue().toString());
//                url_photo = dataSnapshot.child("path").getValue().toString();
//                Glide.with(getApplicationContext()).load(url_photo).into(imageView_item);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


///// buttons intents



        CanceleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(itemProduct.this, MainActivity_profilePage.class);
                startActivity(i);
            }
        });


        delebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Products").child(id+user_id).removeValue();
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("Products").child(id).removeValue();
             FirebaseDatabase.getInstance().getReference("Users").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 String num = dataSnapshot.child("number").getValue().toString();
int num2 = Integer.parseInt(num);
num2--;
String num3 = Integer.toString(num2);
                     FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("number").setValue(num3);                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
                Intent i = new Intent(itemProduct.this, MainActivity_profilePage.class);
                startActivity(i);
            }
        });

// reffrence to the database

        FirebaseUser user = auth.getInstance().getCurrentUser();
        String userid = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");




        imageView_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });


        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item_name.getText().toString().equals("")) {
                    item_name.setError("Please Don't leave this Field Empty ");
                    item_name.findFocus();
                    return;
                }

                if (item_description.getText().toString().equals("")) {
                    item_description.setError("Please Don't leave this Field Empty ");
                    item_description.findFocus();
                    return;
                }


                final String itemname = item_name.getText().toString();
                final String itemDec = item_description.getText().toString();

                user_id  = firebaseUser.getUid();

                FirebaseDatabase.getInstance().getReference("Products").child(id+user_id).child("name").setValue(itemname);
                FirebaseDatabase.getInstance().getReference("Products").child(id+user_id).child("discription").setValue(itemDec);
                FirebaseDatabase.getInstance().getReference("Products").child(id+user_id).child("imageurl").setValue(url_photo);

                // for thhe user tBLE

                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("Products").child(id).child("name").setValue(itemname);
                FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("Products").child(id).child("discription").setValue(itemDec);

                id=itemname;

                startActivity(new Intent(getApplicationContext(), MainActivity_profilePage.class));


            }
        });


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
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data != null ){
                selectedimage=data.getData();
                // itemimage.setImageURI(selectedimage);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                final StorageReference storageRef = storage.getReference("images/Users/profile/"+user_id+"/yy.png");
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
                        FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("Products").child(id).child("path").setValue(url_photo);
                        Glide.with(getApplicationContext()).load(url_photo).into(imageView_item);


                    }
                });











            }// big if 2





        }


}

