package com.example.firebaseautoexample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;

public class ProfilFra extends Fragment {
EditText email,kullaniciadi;
ImageView resimduzenle;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profil,container,false);
        email=v.findViewById(R.id.emailgetir);
        kullaniciadi=v.findViewById(R.id.kullaniciadi);
        resimduzenle=v.findViewById(R.id.resimduzenle);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        kullaniciadi.setText(user.getDisplayName());
        email.setText(user.getEmail());
        resimduzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //duzenle kismi
            }
        });


        return v;
    }


}
