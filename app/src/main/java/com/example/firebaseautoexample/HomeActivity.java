package com.example.firebaseautoexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView text;
    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;
    ImageView imageView;

    @Override
    public void onBackPressed() {
        // drawer aciksa geri tusuna basildiginda onu kapat diyor

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        text=findViewById(R.id.hosgeldiniz);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                String title = "";
                text.setText("");
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new Anasayfa();
                        title = "Anasayfa";
                        break;

                    case R.id.nav_profile:
                        selectedFragment = new ProfilFra();
                        title = "Profil";
                        break;

                    case R.id.nav_logout:
                        new AlertDialog.Builder(HomeActivity.this)
                                .setMessage("Çıkış yapmak istediğinize emin misiniz?")
                                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        firebaseAuth.signOut();
                                        finish();
                                    }
                                })
                                .setNegativeButton("Hayır", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        break;
                    case R.id.kullaniciListe:
                        selectedFragment = new KullaniciListeFra();
                        title = "Kullanici Listesi";
                        break;
                    case R.id.geridonusum:
                        selectedFragment = new SonSilinenlerFra();
                        title = "Geri donusum";
                        break;

                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, selectedFragment).commit();
                }
                getSupportActionBar().setTitle(title);
                drawer.closeDrawer(navigationView);
                return true;
            }


        });

        final View header=navigationView.getHeaderView(0);
        TextView textView=header.findViewById(R.id.textView);
        textView.setText(firebaseUser.getEmail());
        imageView=header.findViewById(R.id.imageView);

        StorageReference storageRef = firebaseStorage.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get().load(uri).fit().centerCrop().into(imageView);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {



            }
        });

        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilFra profilFra=new ProfilFra();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,profilFra).commit();

            }
        });*/
    }


}
