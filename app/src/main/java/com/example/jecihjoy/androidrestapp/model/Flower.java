package com.example.jecihjoy.androidrestapp.model;

import android.graphics.Bitmap;

/**
 * Created by Jecihjoy on 5/22/2018.
 */

public class Flower {

    private int productId;
    private String name;
    private String category;
    private String instructions;
    private  double price;
    private String photo;
    private Bitmap bitmap;

    public Flower(){

    }

    public Flower(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPhoto() {
        return photo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCategory() {
        return category;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getProductId() {
        return productId;
    }


    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
