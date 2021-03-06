package com.example.renad.exchangeit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.renad.exchangeit.Adapter.MyFotoAdapter;
import com.example.renad.exchangeit.Adapter.MySearchAdapter;
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

public class search_products_class extends AppCompatActivity {
    String userid;
    RecyclerView recyclerView;
    MySearchAdapter mySearchAdapter;
    List<SystemProduct> productList;
 User user = new User();
 String user_id_firebase ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_products);

        recyclerView=(RecyclerView)findViewById(R.id.recycle_product);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1= new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager1);
        productList=new ArrayList<>();
        mySearchAdapter =new MySearchAdapter(getApplicationContext(),productList);
        recyclerView.setAdapter(mySearchAdapter);
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
      userid =user.getUid();
        DatabaseReference reference5= FirebaseDatabase.getInstance().getReference("Users");
        reference5.child(userid).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_id_firebase = dataSnapshot.child("id").getValue().toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myFotos();

    }
    private void myFotos(){

        DatabaseReference reference3= FirebaseDatabase.getInstance().getReference("Products");
        reference3.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SystemProduct product=snapshot.getValue(SystemProduct.class);
                    ///user id does not equals userid
                    String idProduct = product.getUseID();
                    if(!(idProduct.equals(userid)))
                    productList.add(product);

                }

                Collections.reverse(productList);
                mySearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
