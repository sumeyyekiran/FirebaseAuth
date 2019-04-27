package com.example.firebaseautoexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class SonSilinenlerFra extends Fragment {
ImageView imageView;
TextView text1,text2;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_son_silinenler,container,false);
        imageView=v.findViewById(R.id.gelenImage);
        text1=v.findViewById(R.id.gelentxt1);
        text2=v.findViewById(R.id.gelentxt2);

        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(v.getContext());
        String savedString = sharedPref.getString("productName","Kayıt Yok");
        String savedString1 = sharedPref.getString("productDescription","Kayıt Yok");
        int savedInt = sharedPref.getInt("silinenId",0);

        imageView.setImageResource(savedInt);
        text1.setText(savedString);
        text2.setText(savedString1);



        return v;
    }
}
