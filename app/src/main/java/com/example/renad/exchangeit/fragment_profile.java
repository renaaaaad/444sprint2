package com.example.renad.exchangeit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class fragment_profile extends Fragment {


    public ImageButton Kitchnbtn;
    public ImageButton bedroombtn;
    public ImageButton livingroombtn;



    public fragment_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);

        Kitchnbtn = (ImageButton)view.findViewById(R.id.kitchen);
        bedroombtn = (ImageButton)view.findViewById(R.id.bedroom);
        livingroombtn = (ImageButton)view.findViewById(R.id.livingroom);


        Kitchnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Seartch_Page.class);

//Create the bundle
                Bundle bundle = new Bundle();

//Add your data to bundle
                bundle.putString("kitchen","Kitchen");

//Add the bundle to the intent
                i.putExtras(bundle);

//Fire that second activity
                startActivity(i);

            }
        });


        Kitchnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_profile.this.getActivity(), Seartch_Page.class));
            }
        });

        bedroombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Seartch_Page.class);

//Create the bundle
                Bundle bundle = new Bundle();

//Add your data to bundle
                bundle.putString("bedroom","bedroom");

//Add the bundle to the intent
                i.putExtras(bundle);

//Fire that second activity
                startActivity(i);

            }
        });


        bedroombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_profile.this.getActivity(), Seartch_Page.class));
            }
        });

        livingroombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Seartch_Page.class);

//Create the bundle
                Bundle bundle = new Bundle();

//Add your data to bundle
                bundle.putString("livingroom","livingroom");

//Add the bundle to the intent
                i.putExtras(bundle);

//Fire that second activity
                startActivity(i);

            }
        });


        livingroombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_profile.this.getActivity(), Seartch_Page.class));
            }
        });

        return view;
    }}
