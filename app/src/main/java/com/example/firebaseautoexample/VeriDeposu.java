package com.example.firebaseautoexample;

import java.util.ArrayList;

public class VeriDeposu {

    private String productName;
    private String productDescription;
    private int imageID;

    public VeriDeposu() {
    }

    public VeriDeposu(String productName, String productDescription, int imageID) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.imageID = imageID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public static ArrayList<VeriDeposu> getData() {
        ArrayList<VeriDeposu> productList = new ArrayList<VeriDeposu>();
        int productImages[] = {R.drawable.desert, R.drawable.exchange, R.drawable.green_tea, R.drawable.key, R.drawable.like, R.drawable.networking, R.drawable.pyramid, R.drawable.rose, R.drawable.smartphone};
        String[] productNames = {"Geleceği Yazanlar", "Paycell", "Tv+", "Dergilik", "Bip", "GNC", "Hesabım", "Sim", "LifeBox"};

        for (int i = 0; i < productImages.length; i++) {
            VeriDeposu temp = new VeriDeposu();
            temp.setImageID(productImages[i]);
            temp.setProductName(productNames[i]);
            temp.setProductDescription("Turkcell");

            productList.add(temp);

        }


        return productList;
    }
}
