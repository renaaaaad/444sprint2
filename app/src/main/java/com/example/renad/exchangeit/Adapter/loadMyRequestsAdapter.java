package com.example.renad.exchangeit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.AcceptedRequest;
import com.example.renad.exchangeit.R;
import com.example.renad.exchangeit.SquareimageView;
import com.example.renad.exchangeit.SystemProduct;
import com.example.renad.exchangeit.requestDetaile;
import com.example.renad.exchangeit.requestProductDetails;
import com.example.renad.exchangeit.reviewMyRequests;
import com.example.renad.exchangeit.search_item_product;
import com.example.renad.exchangeit.user_Requests;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class loadMyRequestsAdapter extends RecyclerView.Adapter<loadMyRequestsAdapter.ViewHolder>{
    private Context context;
    private List<user_Requests> mPosts;
    private TextView name , status ;
    private ImageView imageView , button;



    public loadMyRequestsAdapter(Context context, List<user_Requests> mPosts) {
        this.context = context;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public loadMyRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myrequestslist, parent, false);
        name = view.findViewById(R.id.name);
        status = view.findViewById(R.id.status);
        imageView = view.findViewById(R.id.product_image);
        button = view.findViewById(R.id.img);


        return new loadMyRequestsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull loadMyRequestsAdapter.ViewHolder holder, int position) {
       user_Requests user_requests =  mPosts.get(position);
     String recive =   user_requests.getRecive_user() ;
     String productRecive = user_requests.getRecive_product();
     final String me = user_requests.getInitial_user();
     String my_product = user_requests.getInitial_product() ;
int id = user_requests.getId() ;
final String id2 = Integer.toString(id);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(recive).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
name.setText(dataSnapshot.child("fname").getValue().toString()+" "+dataSnapshot.child("lname").getValue().toString());
                Glide.with(context).load(dataSnapshot.child("imageurl").getValue().toString()).into(imageView);
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(me);
                reference2.child("Initiate_requests").child(id2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        status.setText(dataSnapshot.child("status").getValue().toString());
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



    }// onBindViewHolder

    @Override
    public int getItemCount() {
         return mPosts.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public SquareimageView search_image;

        public ViewHolder(View itemView) {
            super(itemView);
            final int position = getAdapterPosition();
            button = itemView.findViewById(R.id.img);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();


                    user_Requests user_requests1 = mPosts.get(position);
                    String status3 = user_requests1.getStatus() ;


                        Intent intent = new Intent(context, reviewMyRequests.class);
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
            });



        }
    }




}
