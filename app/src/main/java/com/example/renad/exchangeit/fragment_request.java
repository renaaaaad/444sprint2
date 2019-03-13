package com.example.renad.exchangeit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.renad.exchangeit.Adapter.MyFotoAdapter;
import com.example.renad.exchangeit.Adapter.loadRequestsAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class fragment_request extends Fragment {
RecyclerView recyclerView ;
    loadRequestsAdapter  loadRequestsAdapter ;
    List<user_Requests> user_requests ;
    TextView textView ;
    Button myRequests ;
    String requests_rec ;
    public fragment_request() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        View view = inflater.inflate(R.layout.fragment_fragment_request, container, false);
        myRequests = view.findViewById(R.id.button11);

                recyclerView=(RecyclerView)view.findViewById(R.id.recycleRequest);
                recyclerView.setNestedScrollingEnabled(true);
        textView = (TextView)view.findViewById(R.id.textView11);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager1= new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(linearLayoutManager1);
        user_requests =new ArrayList<>();
        loadRequestsAdapter=new loadRequestsAdapter(getContext(),user_requests);
        recyclerView.setAdapter(loadRequestsAdapter);
myRequests(userid);


myRequests.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Fragment fragment = new myRequests();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return;
    }
});


        return view;

    }// on creat

public void myRequests(final String id ){

    DatabaseReference reference3=   FirebaseDatabase.getInstance().getReference("Users").child(id);
    reference3.child("Receive_requestsNum").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            if (dataSnapshot.getValue().toString().equals("0")){
                textView.setVisibility(TextView.VISIBLE);
                return;
            }

            DatabaseReference reference4=FirebaseDatabase.getInstance().getReference("Users");
            reference4.child(id).child("requestsReceive").addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user_requests.clear();

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        user_Requests user_requests1 =snapshot.getValue(user_Requests.class);
                        user_requests.add(user_requests1);

                    }

                    Collections.reverse(user_requests);
                    loadRequestsAdapter.notifyDataSetChanged();
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


//--------------------------------------------------------------------------------------------------

}// the end of the finction

}
