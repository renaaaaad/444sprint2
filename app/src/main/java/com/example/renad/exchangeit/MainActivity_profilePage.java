package com.example.renad.exchangeit;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.Adapter.MyFotoAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity_profilePage extends AppCompatActivity {
    private BottomNavigationView navigation;
    private TextView name_text , city2;
    private String name_from_firebase;
    private String user_id;
    private String user_name;
    Button cancel ;

     RecyclerView recyclerView ;
     MyFotoAdapter myFotoAdapter;
     List<Product> productList;

     /////Nora
     ImageView image_profile;

     FirebaseUser firebaseUser;

     String profileid;

     ////

    private myPHotoAdapter myPHotoAdapter ;
    private List<Product> list_product ;

    //------------------------------------------------ the data base varible
  private FirebaseAuth firebaseAuth;
  private FirebaseDatabase firebaseDatabase;
  private FirebaseAuth.AuthStateListener authStateListener;
  private DatabaseReference databaseReference;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile_page);
        name_text = (TextView)findViewById(R.id.name);
city2 =        (TextView)findViewById(R.id.city);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences perfs=getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid=perfs.getString("profileid","none");

        image_profile=findViewById(R.id.imageProfile);


        String userid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                   @Override
                                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                       name_text.setText(dataSnapshot.child("fname").getValue().toString());
                                                                       city2.setText(dataSnapshot.child("city").getValue().toString());
                                                                       String url_photo = dataSnapshot.child("imageurl").getValue().toString();
                                                                       Glide.with(getApplicationContext()).load(url_photo).into(image_profile);

                                                                   }

                                                                   @Override
                                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                   }
                                                               });
//String nname = databaseReference.child("Users").child(user_id).child("fname").toString();

//recyclerView = (RecyclerView)findViewById(R.id.recycle);
//recyclerView.setHasFixedSize(true);
//LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),3);
//recyclerView.setLayoutManager(linearLayoutManager);
//list_product = new ArrayList<>();
//myPHotoAdapter = new myPHotoAdapter(getApplicationContext(),list_product);
//recyclerView.setAdapter(myPHotoAdapter);


        //show product

        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1= new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager1);
        productList=new ArrayList<>();
        myFotoAdapter=new MyFotoAdapter(getApplicationContext(),productList);
        recyclerView.setAdapter(myFotoAdapter);

        myFotos(userid);

//        myProducts(userid);





                navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
cancel  = (Button)findViewById(R.id.button6);
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        firebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),loginPage.class));
    }
});
    }//oncreat

    private void showdata(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds :dataSnapshot.getChildren()){
            User user_new = new User();
            user_new.setFname(ds.child(user_id).getValue(User.class).getFname());
            user_new.setLname(ds.child(user_id).getValue(User.class).getLname());
            user_new.setCity(ds.child(user_id).getValue(User.class).getCity());
            user_new.setFname(ds.child(user_id).getValue(User.class).getFname());
            user_new.setEmail(ds.child(user_id).getValue(User.class).getEmail());
            user_new.setPhoneNumber(ds.child(user_id).getValue(User.class).getPhoneNumber());
            user_new.setId(user_id);
            name_text.setText(user_new.getFname());
        }//for part


    }
 BottomNavigationView.OnNavigationItemSelectedListener  mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragment_profile fragment = new fragment_profile();
            fragment_seartch fragment_seartch = new fragment_seartch();
            fragment_sitting_option fragment_sitting = new fragment_sitting_option();
            fragment_request fragment_request = new fragment_request();
            switch (item.getItemId()) {
                case R.id.search:
                    loadFragment(fragment);
                    return true;
                //-------------------------------------------------------------
                case R.id.profile:
                    loadFragment(fragment_seartch);
                    return true;
                //------------------------------------------------------
                case R.id.sitting:
                    loadFragment(fragment_sitting);
                    return true;
                //---------------------------------------------------
                case R.id.requests:
                    loadFragment(fragment_request);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(fragment_request fragment_reqest) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment_reqest);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragment(fragment_sitting_option fragment_sitting) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment_sitting);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragment(fragment_seartch fragment_seartch) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment_seartch);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragment(fragment_profile fragment) {

    }


    public void Add(View view) {
        Intent intent2 = new Intent(getApplicationContext(),addProduct.class);
        intent2.putExtra("User_Id",user_id);
        startActivity(intent2);
    }



    private void myFotos(String id){

        DatabaseReference reference3=FirebaseDatabase.getInstance().getReference("Users");
        reference3.child(id).child("Products").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product=snapshot.getValue(Product.class);
                    productList.add(product);

                }

                Collections.reverse(productList);
                myFotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
