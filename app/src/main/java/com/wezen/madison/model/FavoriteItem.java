package com.wezen.madison.model;

import android.graphics.Bitmap;

/**
 * Created by eder on 5/15/15.
 */
public class FavoriteItem {

    private Integer favImage;
    private String favName;
    private String favPrice;

    public FavoriteItem(){}

    public FavoriteItem(Integer image, String name, String price){
        setFavImage(image);
        setFavName(name);
        setFavPrice(price);
    }

    public Integer getFavImage() {
        return favImage;
    }

    public void setFavImage(Integer favImage) {
        this.favImage = favImage;
    }

    public String getFavName() {
        return favName;
    }

    public void setFavName(String favName) {
        this.favName = favName;
    }

    public String getFavPrice() {
        return favPrice;
    }

    public void setFavPrice(String favPrice) {
        this.favPrice = favPrice;
    }
}
