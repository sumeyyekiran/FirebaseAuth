package com.example.firebaseautoexample;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KullaniciAdapter extends RecyclerView.Adapter<KullaniciAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;
    public KullaniciAdapter(Context context, List<User> userList) {

        this.userList = userList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView userEmail;
        public ImageView userPhoto;
        public ImageView optionMenu;

        public ViewHolder(View view) {
            super(view);

            userName = view.findViewById(R.id.userName);
            userEmail = view.findViewById(R.id.userEmail);
            userPhoto = view.findViewById(R.id.userPicture);
            optionMenu = view.findViewById(R.id.optionMenu);

        }

    }

    @Override
    public KullaniciAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.userName.setText(userList.get(position).getName());
        viewHolder.userEmail.setText(userList.get(position).getEmail());

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("users").child(userList.get(position).getUuid());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get().load(uri).fit().centerCrop().into(viewHolder.userPhoto);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {



            }
        });



        viewHolder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users").child(userList.get(position).getUuid());
                databaseReference.removeValue();

                /*PopupMenu popup = new PopupMenu(context, holder.optionMenu);
                popup.inflate(R.menu.menu_items);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                //update customer
                                updateCustomer(customerList.get(position));
                                break;
                            case R.id.delete:
                                //delete customer
                                deleteCustomer(customerList.get(position).getCustomerId());
                                break;
                        }
                        return false;
                    }
                });
                popup.show();*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
