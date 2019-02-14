package com.example.renad.exchangeit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renad.exchangeit.MainActivity_profilePage;
import com.example.renad.exchangeit.Product;
import com.example.renad.exchangeit.R;
import com.example.renad.exchangeit.SystemProduct;
import com.example.renad.exchangeit.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public Context mContext;
    public List<Product> mProduct;
    public List<SystemProduct> mSProduct;



    private FirebaseUser firebaseUser;

    public ProductAdapter(Context mContext, List<Product> mProduct) {
        this.mContext = mContext;
        this.mProduct = mProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.product_item,viewGroup,false);

        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Product product=mProduct.get(i);
        final SystemProduct systemProduct=mSProduct.get(i);

        Glide.with(mContext).load(product.getPath()).into(viewHolder.post_image);


        if(product.getDiscription().equals("")){
            viewHolder.description.setVisibility(View.GONE);
        }
        else{
            viewHolder.description.setVisibility(View.VISIBLE);
            viewHolder.description.setText(product.getDiscription());

        }



        //9:20
        publisherInfo(viewHolder.username);

//        viewHolder //6:39

        viewHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",systemProduct.getUseID());
                editor.apply();

//                (mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new MainActivity_profilePage()).commit();

            }
        });



    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

//        public ImageView image_profile;

        public ImageView post_image;

        public TextView username,description;


        public ViewHolder(View itemView) {
            super(itemView);

            post_image=itemView.findViewById(R.id.post_image);
            username=itemView.findViewById(R.id.username);
            description=itemView.findViewById(R.id.description);

        }
    }

//    private void publisherInfo (ImageView image_profile, TextView username, String userid)

    private void publisherInfo (final TextView username){

        /// to be deleted this is for user id

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String userid=firebaseUser.getUid();

        ///done


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
//                Glide.with(mContext).load(user.getImage()).into(image_profile);
                username.setText(user.getFname());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
