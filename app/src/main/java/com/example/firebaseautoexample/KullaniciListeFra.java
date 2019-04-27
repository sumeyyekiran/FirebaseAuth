package com.example.firebaseautoexample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class KullaniciListeFra extends Fragment {

   private RecyclerView recyclerView;
   private KullaniciAdapter adapter;
   private DatabaseReference reference;
   private List<User> userList;
   private RecyclerView.LayoutManager layoutManager;

   @Override
   public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
      View v=inflater.inflate(R.layout.fragment_kullanici_liste,container,false);

      reference= FirebaseDatabase.getInstance().getReference("users");
      userList=new ArrayList<>();

      recyclerView=v.findViewById(R.id.recylerview);


      reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            userList.clear();
            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
               User user=postSnapshot.getValue(User.class);
               userList.add(user);
            }

            layoutManager=new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter=new KullaniciAdapter(getActivity(),userList);
            recyclerView.setAdapter(adapter);

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {
//database hatalari oldugunda buraya duser
         }
      });




      return v;
   }
}
