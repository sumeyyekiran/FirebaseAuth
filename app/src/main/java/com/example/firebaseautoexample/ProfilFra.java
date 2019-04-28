package com.example.firebaseautoexample;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class ProfilFra extends Fragment {
EditText email,kullaniciadi,parolaguncelle;
ImageView resim;
Button button,duzenle;
FirebaseStorage firebaseStorage;
FirebaseAuth firebaseAuth;
Uri uri;
ProgressDialog progressDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==121 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            uri=data.getData();
            Picasso.get().load(data.getData()).fit().centerCrop().into(resim);
        }

    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profil,container,false);
        email=v.findViewById(R.id.userEmailUpdate);
        kullaniciadi=v.findViewById(R.id.userNicknameUpdate);
        resim=v.findViewById(R.id.userProfileIcon);
        button=v.findViewById(R.id.updateBttn);
        parolaguncelle=v.findViewById(R.id.userPasswordUpdate);
        duzenle=v.findViewById(R.id.guncellebtn);



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //kullaniciadi.setText(user.getDisplayName());

        email.setText(user.getEmail());

        duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");//her resim dosyasi olur png-jpg-...
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Resim seciniz"),121);
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StorageReference storageRef = firebaseStorage.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                progressDialog.dismiss();
                Picasso.get().load(uri).fit().centerCrop().into(resim);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gelenisim=kullaniciadi.getText().toString();
                kullaniciadi.setText(gelenisim);

                if(uri!=null){

                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Yükleniyor...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    StorageReference storageRef = firebaseStorage.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
                    storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Fotoğraf başarılı bir şekilde kaydedildi.", Toast.LENGTH_SHORT).show();
                            resim.setImageBitmap(null);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();//donmemesi icin
                            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        });


        return v;
    }


}
