package com.example.renad.exchangeit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<String> fnameList;
    ArrayList<String> lnameList;

    class SearchViewHolder extends RecyclerView.ViewHolder
    {
        TextView fname,lname;

        public SearchViewHolder(View itemView) {
            super(itemView);
            fname=(TextView)itemView.findViewById(R.id.fname);
            lname=(TextView) itemView.findViewById(R.id.lname);
        }
    }


    public searchAdapter(Context context, ArrayList<String> fnameList, ArrayList<String> lnameList) {
        this.context = context;
        this.fnameList = fnameList;
        this.lnameList = lnameList;
    }

    @NonNull
    @Override
    public searchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //To access Layout class
        View view= LayoutInflater.from(context).inflate(R.layout.search_list_layout,parent,false);
        return new searchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.lname.setText(lnameList.get(position));
        holder.fname.setText(fnameList.get(position));


    }


    @Override
    public int getItemCount() {
        return fnameList.size();
    }
}