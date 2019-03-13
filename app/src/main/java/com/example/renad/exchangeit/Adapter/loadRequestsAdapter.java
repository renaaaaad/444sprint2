package com.example.renad.exchangeit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.AcceptedRequest;
import com.example.renad.exchangeit.Product;
import com.example.renad.exchangeit.R;
import com.example.renad.exchangeit.SquareimageView;
import com.example.renad.exchangeit.SystemProduct;
import com.example.renad.exchangeit.fragment.ProductDetailsFragment;
import com.example.renad.exchangeit.itemProduct;
import com.example.renad.exchangeit.requestDetaile;
import com.example.renad.exchangeit.requestProductDetails;
import com.example.renad.exchangeit.user_Requests;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class loadRequestsAdapter extends RecyclerView.Adapter<loadRequestsAdapter.ViewHolder>{

    private Context context;
    private List<user_Requests> mPosts;
    SystemProduct systemProduct;
String initiate_user , recive_user ;
String getInitiate_product , recive_producr , status;
int id_request ;
ImageView imageView ;
TextView name , String_status , phonenumber ;
    LinearLayout linearLayout ;
private String id_image;
ImageView button ;
String status2 ;


    public loadRequestsAdapter(Context context, List<user_Requests> mPosts) {
        this.context = context;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.requests_item,viewGroup,false);
        phonenumber = view.findViewById(R.id.phoneNumber);
        String_status = view.findViewById(R.id.status);
       imageView = view.findViewById(R.id.product_image);
        name = view.findViewById(R.id.name);

        return new loadRequestsAdapter.ViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {




        user_Requests user_requests = mPosts.get(i);



        initiate_user = user_requests.getInitial_user();
        recive_user = user_requests.getRecive_user();
        status = user_requests.getStatus();
        id_request = user_requests.getId();
        recive_producr = user_requests.getRecive_product();
        getInitiate_product = user_requests.getInitial_product();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(initiate_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
    String fname = dataSnapshot.child("fname").getValue().toString();
  String lname = dataSnapshot.child("lname").getValue().toString();
  String fullname = fname+" "+lname ;
                name.setText(fullname);
      // we get the id of the request to get it is status
      String idString = Integer.toString(id_request);
 DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(initiate_user).child("Initiate_requests");
 reference2.child(idString).addListenerForSingleValueEvent(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
         String_status.setText("Status : "+dataSnapshot.child("status").getValue().toString());
         status2 = dataSnapshot.child("status").getValue().toString() ;
         if(status2.equals("Accepted")){

             DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
             reference.child(initiate_user).addListenerForSingleValueEvent(new ValueEventListener() {
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
 });// the end if status
                // load image
    String img =   dataSnapshot.child("imageurl").getValue().toString();
  Glide.with(context).load(img).into(viewHolder.imageView);




            }// on data change

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return mPosts.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public SquareimageView post_image;
        public ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            final user_Requests user_requests ;

/////
            imageView = itemView.findViewById(R.id.product_image);
            phonenumber = itemView.findViewById(R.id.phoneNumber);
            String_status = itemView.findViewById(R.id.status);
           imageView = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.name);

/////
            // to go to detail page
            button = itemView.findViewById(R.id.img);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();


                    user_Requests user_requests1 = mPosts.get(position);
String status3 = user_requests1.getStatus() ;

                    if(status3.equals("Accepted"))
                    {
                        Intent intent = new Intent(context, AcceptedRequest.class);
                        String int_user  = user_requests1.getInitial_user();
                        String int_prod = user_requests1.getInitial_product();
                        String rec_user = user_requests1.getRecive_user();
                        String rec_prod = user_requests1.getRecive_product();
                        int id = user_requests1.getId() ;
                        String id2 = Integer.toString(id);

                        requestProductDetails requestProductDetails2 = user_requests1.getRequestProductDetails() ;
                        intent.putExtra("pro_intiate",requestProductDetails2.getIntiate_path());
                        intent.putExtra("pro_des",requestProductDetails2.getP_des());
                        intent.putExtra("pro_name",requestProductDetails2.getP_name());
                        intent.putExtra("pro_recive",requestProductDetails2.getRecive_path());
                        intent.putExtra("pro_user",requestProductDetails2.getRecive_name());
                        intent.putExtra("id",int_user);


                        context.startActivity(intent);
return;
                    }


                        Intent intent = new Intent(context, requestDetaile.class);
String int_user  = user_requests1.getInitial_user();
String int_prod = user_requests1.getInitial_product();
String rec_user = user_requests1.getRecive_user();
String rec_prod = user_requests1.getRecive_product();
int id = user_requests1.getId() ;
String id2 = Integer.toString(id);
intent.putExtra("int_user",int_user);
intent.putExtra("int_prod",int_prod);
intent.putExtra("rec_user",rec_user);
intent.putExtra("rec_prod",rec_prod);
intent.putExtra("id",id2);

context.startActivity(intent);



                }
            });
        }
    }

//    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//                reference.child(initiate_user).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            // the name
//            String fname = dataSnapshot.child("fname").getValue().toString();
//            String lname = dataSnapshot.child("lname").getValue().toString();
//            name.setText(fname+" "+lname);
//            // the status
//            String idString = Integer.toString(id_request);
//            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(initiate_user);
//            reference2.child(idString).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String path =  dataSnapshot.child("path").getValue().toString();
//                    Glide.with(context).load(path).into(imageView);
//                }
//
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    });
}
