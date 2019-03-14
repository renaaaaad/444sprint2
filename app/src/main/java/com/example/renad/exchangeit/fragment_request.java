package com.example.renad.exchangeit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.Adapter.loadMyRequestsAdapter;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class fragment_request extends Fragment {

    RecyclerView recyclerView ;
    loadMyRequestsAdapter loadRequestsAdapter ;
    List<user_Requests> user_requests ;
    TextView textView ;
    String requests_rec ;
    LinearLayout layout_big ;
    String status2  ;
    Button myReqbtn;


    public fragment_request() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_request, container, false);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();

        layout_big =  view.findViewById(R.id.container);
        layout_big.setOrientation(LinearLayout.VERTICAL);

        textView = (TextView)view.findViewById(R.id.textView11);

        LinearLayoutManager linearLayoutManager1= new GridLayoutManager(getContext(),1);


        myReqbtn=(Button)view.findViewById(R.id.button11);
        myReqbtn.setOnClickListener(new View.OnClickListener() {
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



        user_requests =new ArrayList<>();
        myRequests(userid);

        return view;

    }// on creat





public void myRequests(final String id ) {

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
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user_requests.clear();

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        user_Requests user_requests1 =snapshot.getValue(user_Requests.class);
                        user_requests.add(user_requests1);

                    }// for



                    int size = user_requests.size();
                    for (int i =0 ; i<size;i++) {
                        final LinearLayout linearLayout2 = new LinearLayout(getContext());
                        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout linearLayout3 = new LinearLayout(getContext());
                        linearLayout3.setOrientation(LinearLayout.VERTICAL);
                        final CircleImageView circleImageView = new CircleImageView(getContext());

                        final TextView phonenumber = new TextView(getContext());
                        final TextView fullname = new TextView(getContext());
                        final TextView status = new TextView(getContext());
                        //  TextView lname = new TextView(getContext());
                        ImageView arrow = new ImageView(getContext());
                        arrow.setId(i);
                        arrow.setBackgroundResource(R.drawable.ic_keyboard_arrow_right_black_24dp);

                        final user_Requests user_requests2 = user_requests.get(i);
                        final   String inti =   user_requests2.getInitial_user() ;
                        String productInint = user_requests2.getInitial_product();
                        final String me = user_requests2.getRecive_user();
                        String my_product = user_requests2.getRecive_product() ;
                        int id = user_requests2.getId() ;
                        final String id2 = Integer.toString(id);
                        // get from the database , do the reference
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        reference.child(inti).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                fullname.setText(dataSnapshot.child("fname").getValue().toString()+" "+dataSnapshot.child("lname").getValue().toString());

                                Glide.with(getContext()).load(dataSnapshot.child("imageurl").getValue().toString()).into(circleImageView);
                                circleImageView.getLayoutParams().height = 88;
                                circleImageView.getLayoutParams().width = 88;

                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(me);
                                reference2.child("requestsReceive").child(id2).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                        //-------------
                                        status.setText(dataSnapshot.child("status").getValue().toString());
                                        if (dataSnapshot.child("status").getValue().toString().equals("Accepted")) {
                                            linearLayout2.setOnTouchListener(new View.OnTouchListener() {
                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    boolean returnValue = true;

                                                    if (event.getAction() == MotionEvent.ACTION_UP) { //on touch release
                                                        Intent intent;

                                                        returnValue = false; //prevent default action on release
                                                        intent = new Intent(getContext(), AcceptedRequest.class);
                                                        String int_user = user_requests2.getInitial_user();
                                                        String int_prod = user_requests2.getInitial_product();
                                                        String rec_user = user_requests2.getRecive_user();
                                                        String rec_prod = user_requests2.getRecive_product();
                                                        int id = user_requests2.getId();
                                                        String id2 = Integer.toString(id);

                                                        requestProductDetails requestProductDetails2 = user_requests2.getRequestProductDetails();
                                                        intent.putExtra("pro_intiate", requestProductDetails2.getIntiate_path());
                                                        intent.putExtra("pro_des", requestProductDetails2.getP_des());
                                                        intent.putExtra("pro_name", requestProductDetails2.getP_name());
                                                        intent.putExtra("pro_recive", requestProductDetails2.getRecive_path());
                                                        intent.putExtra("pro_user", requestProductDetails2.getRecive_name());
                                                        intent.putExtra("id", int_user);
                                                        startActivity(intent);

                                                    }



                                                    return returnValue;
                                                }

                                            });
                                        }

                                        if (!(dataSnapshot.child("status").getValue().toString().equals("Accepted"))) {
                                            linearLayout2.setOnTouchListener(new View.OnTouchListener() {
                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    boolean returnValue = true;

                                                    if (event.getAction() == MotionEvent.ACTION_UP) { //on touch release
                                                        Intent intent;

                                                        returnValue = false; //prevent default action on release
                                                        intent = new Intent(getContext(), requestDetaile.class);
                                                        String int_user = user_requests2.getInitial_user();
                                                        String int_prod = user_requests2.getInitial_product();
                                                        String rec_user = user_requests2.getRecive_user();
                                                        String rec_prod = user_requests2.getRecive_product();
                                                        int id = user_requests2.getId();
                                                        String id2 = Integer.toString(id);

                                                        requestProductDetails requestProductDetails2 = user_requests2.getRequestProductDetails();
                                                        intent.putExtra("pro_intiate", requestProductDetails2.getIntiate_path());
                                                        intent.putExtra("pro_des", requestProductDetails2.getP_des());
                                                        intent.putExtra("pro_name", requestProductDetails2.getP_name());
                                                        intent.putExtra("pro_recive", requestProductDetails2.getRecive_path());
                                                        intent.putExtra("pro_user", requestProductDetails2.getRecive_name());
                                                        intent.putExtra("pro_user", requestProductDetails2.getRecive_name());

                                                        intent.putExtra("int_user", int_user);
                                                        intent.putExtra("rec_user", rec_user);
                                                        intent.putExtra("int_prod", int_prod);
                                                        intent.putExtra("rec_prod", rec_prod);




                                                        intent.putExtra("id", int_user);
                                                        startActivity(intent);

                                                    }
                                                    ;


                                                    return returnValue;
                                                }

                                            });
                                        }
//--------------------
//status2 = dataSnapshot.child("status").getValue().toString();
                                        if ((dataSnapshot.child("status").getValue().toString()).equals("Accepted")){
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                            reference.child(inti).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    phonenumber.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });// the first listener
                        linearLayout2.setClickable(true);
                        linearLayout2.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                boolean returnValue = true;

                                if(event.getAction()==MotionEvent.ACTION_UP) { //on touch release
                                    Intent intent ;
                                    if (status2.equals("Accepted")) {
                                        returnValue = false; //prevent default action on release
                                        intent = new Intent(getContext(), AcceptedRequest.class);
                                        String int_user = user_requests2.getInitial_user();
                                        String int_prod = user_requests2.getInitial_product();
                                        String rec_user = user_requests2.getRecive_user();
                                        String rec_prod = user_requests2.getRecive_product();
                                        int id = user_requests2.getId();
                                        String id2 = Integer.toString(id);

                                        requestProductDetails requestProductDetails2 = user_requests2.getRequestProductDetails();
                                        intent.putExtra("pro_intiate", requestProductDetails2.getIntiate_path());
                                        intent.putExtra("pro_des", requestProductDetails2.getP_des());
                                        intent.putExtra("pro_name", requestProductDetails2.getP_name());
                                        intent.putExtra("pro_recive", requestProductDetails2.getRecive_path());
                                        intent.putExtra("pro_user", requestProductDetails2.getRecive_name());
                                        intent.putExtra("id", int_user);


                                    } else {
                                        returnValue = false;
                                        intent = new Intent(getContext(), requestDetaile.class);
                                        String int_user = user_requests2.getInitial_user();
                                        String int_prod = user_requests2.getInitial_product();
                                        String rec_user = user_requests2.getRecive_user();
                                        String rec_prod = user_requests2.getRecive_product();
                                        int id = user_requests2.getId();
                                        String id2 = Integer.toString(id);
                                        intent.putExtra("int_user", int_user);
                                        intent.putExtra("int_prod", int_prod);
                                        intent.putExtra("rec_user", rec_user);
                                        intent.putExtra("rec_prod", rec_prod);
                                        intent.putExtra("id", id2);


                                    }
                                    startActivity(intent);

                                }
                                return returnValue;                                                                 }

                        });


                        setMargins(fullname, 40, 40, 40, 40);
                        setMargins(linearLayout2,5,100,5,100);
                        setMargins(linearLayout3,5,5,5,5);
                        setMargins(status,5,5,5,5);
                        setMargins(circleImageView,5,5,5,5);

                        linearLayout3.addView(fullname);
                        linearLayout3.addView(status);
                        linearLayout3.addView(phonenumber);
                        linearLayout2.addView(circleImageView);
                        linearLayout2.addView(linearLayout3);
                        linearLayout2.addView(arrow);

                        layout_big.addView(linearLayout2);



                    }// for


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

}

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}




