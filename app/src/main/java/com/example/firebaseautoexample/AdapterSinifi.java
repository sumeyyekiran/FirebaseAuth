package com.example.firebaseautoexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSinifi extends RecyclerView.Adapter<AdapterSinifi.MyViewHolder>  {

    ArrayList<VeriDeposu> mProductList;
    LayoutInflater inflater;
    public int silinenId;
    String productName;
    String productDescription;


    public AdapterSinifi(Context context, ArrayList<VeriDeposu> products) {
        inflater = LayoutInflater.from(context);
        this.mProductList = products;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VeriDeposu selectedProduct = mProductList.get(position);
        holder.setData(selectedProduct, position);

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView productName, productDescription;
        ImageView productImage, deleteproduct;
        int position;




        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productDescription = (TextView) itemView.findViewById(R.id.productDescription);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            deleteproduct = (ImageView) itemView.findViewById(R.id.deleteproduct);
            deleteproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    silme(position,v);
                }
            });

        }

        public void setData(VeriDeposu selectedProduct, int position) {

            this.productName.setText(selectedProduct.getProductName());
            this.productDescription.setText(selectedProduct.getProductDescription());
            this.productImage.setImageResource(selectedProduct.getImageID());


        }

        @Override
        public void onClick(View v) {


        }


    }

    public void silme(int position,View v) {
        mProductList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mProductList.size());

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        VeriDeposu silinen=mProductList.get(position);
        silinenId=silinen.getImageID();
        productName=silinen.getProductName();
        productDescription=silinen.getProductDescription();

        editor.putInt("silinenId",silinenId); //int değer ekleniyor
        editor.putString("productName",productName); //int değer ekleniyor
        editor.putString("productDescription",productDescription); //int değer ekleniyor
        editor.commit(); //Kayıt

    }

}
