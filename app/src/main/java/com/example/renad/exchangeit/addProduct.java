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
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.MainActivity_profilePage;
import com.example.renad.exchangeit.Product;
import com.example.renad.exchangeit.R;
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
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class addProduct extends AppCompatActivity {


    private static final  int RESULT_LOAD_IMAGE=1;

    private static Button image_insert ;
    private Button cancel;
    private Button add ;
    ImageView itemimage;
    TextView productname , image_error;
    EditText productdiscription;
    Spinner category;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private StorageReference mStorageRef;
    private Uri selectedimage;
    String user_id;
    String path;
    SystemProduct systemProduct;
    private  int product_nnumber  ;
    String product_nnumber_String ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(firebaseUser.getUid());
            user_id  = firebaseUser.getUid();
        }




        image_insert = (Button)findViewById(R.id.explor_phone) ;
        cancel = (Button) findViewById(R.id.cancel_home);
        itemimage =(ImageView) findViewById(R.id.itemimage);
        add=(Button)findViewById(R.id.add_button);
        productname=findViewById(R.id.product_name);
        image_error = (TextView)findViewById(R.id.textView9) ;
        productdiscription=(EditText)findViewById(R.id.editText6);
        category=findViewById(R.id.category);
        mStorageRef= FirebaseStorage.getInstance().getReference();








        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity_profilePage.class));
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(productname.getText().toString().equals("")){
                    productname.setError(" Please Enter The Name of the Product ");
                    productname.findFocus();
                    return;
                }

                if(productdiscription.getText().toString().equals("")){
                    productdiscription.setError(" Please Enter Description of the Product ");
                    productdiscription.findFocus();
                    return;
                }
                final String p_des =  productdiscription.getText().toString();


                final String p_name =  productname.getText().toString();
                final String p_cat =  category.getSelectedItem().toString();

                if(selectedimage!=null){
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    final StorageReference storageRef = storage.getReference("images/Users/+"+user_id+"/"+p_name+".jpg");
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
                            path = uri.toString();

                            // to get the number of products

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String number_of_products = dataSnapshot.child("number").getValue().toString();
                                    product_nnumber = Integer.parseInt(number_of_products);
                                    product_nnumber++;
                                    product_nnumber_String =String.valueOf(product_nnumber);

                                    FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("product_number").setValue(product_nnumber_String);

                                    Product product= new Product(p_name,p_des,p_cat,path,user_id,product_nnumber_String );
product.setId(user_id);
                                    // to stor the dproduct to the user and the alon (table)


                                    String proID=path.substring(8);
                                    systemProduct=new SystemProduct(p_name,p_des,p_cat,path,user_id,p_name+user_id );


                                    DatabaseReference ref = database.getInstance().getReference("Products");
                                    ref.child(p_name+user_id).setValue(systemProduct);
                                    product.setId(ref.push().getKey());



                                    firebaseDatabase.child("Products").child(product_nnumber_String).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                startActivity( new Intent(getApplicationContext(),MainActivity_profilePage.class));
                                            }
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });




                            //--------------------------------------------------





















                        }
                    });





                }//if
                else {
                    image_error.setError(" Please Choose Image  ");
                    image_error.findFocus();
                    return;
                }









            }//on click
        });



        image_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data != null ){
            selectedimage=data.getData();
            itemimage.setImageURI(selectedimage);


        }






    }

}