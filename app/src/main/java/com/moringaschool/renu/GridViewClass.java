package com.moringaschool.renu;

import android.graphics.Typeface;

import java.io.Serializable;

public class GridViewClass implements Serializable {

    public String foodName;
    public String foodPrice;
    public int image;

    public GridViewClass(String foodName, String foodPrice, int image) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
