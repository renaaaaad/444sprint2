package com.example.renad.exchangeit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.example.renad.exchangeit.searchAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

public class Seartch_Page extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> fnameList;
    ArrayList<String> lnameList;
     searchAdapter searchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nora_page);



        search_edit_text=(EditText)findViewById(R.id.search_edit_text);
        recyclerView=(RecyclerView)findViewById(R.id.recycleView);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));


        fnameList =new ArrayList<>();
        lnameList =new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    setAdapter(s.toString());


                }else{
                    //if is it empty
                    //clear list every time
                    fnameList.clear();
                    lnameList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {


        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear list every time
                fnameList.clear();
                lnameList.clear();
                recyclerView.removeAllViews();
                //limit search ex 15 results
                int counter=0;

                //To take each value from database using for loop
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String userId=snapshot.getKey();
                    String fname= snapshot.child("fname").getValue(String.class);
                    String lname =snapshot.child("lname").getValue(String.class);

                    if(fname.toLowerCase().contains(searchedString.toLowerCase())){
                        fnameList.add(fname);
                        lnameList.add(lname);

                    }else if(lname.toLowerCase().contains(searchedString.toLowerCase())){
                        fnameList.add(fname);
                        lnameList.add(lname);
                        counter++;

                    }

                    if(counter==15){
                        break;
                    }
                    searchAdapter= new searchAdapter(Seartch_Page.this,fnameList,lnameList);
                    recyclerView.setAdapter(searchAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
