package com.wezen.madison.model;

import android.graphics.Bitmap;

/**
 * Created by eder on 5/18/15.
 */
public class BeverageMenu {
    private byte[] beverageMenuImage;
    private String beverageMenuName;

    public BeverageMenu(){}

    public BeverageMenu(byte[] beverageMenuImage, String beverageMenuName){
        setBeverageMenuImage(beverageMenuImage);
        setBeverageMenuName(beverageMenuName);
    }

    public byte[] getBeverageMenuImage() {
        return beverageMenuImage;
    }

    public void setBeverageMenuImage(byte[] beverageMenuImage) {
        this.beverageMenuImage = beverageMenuImage;
    }

    public String getBeverageMenuName() {
        return beverageMenuName;
    }

    public void setBeverageMenuName(String beverageMenuName) {
        this.beverageMenuName = beverageMenuName;
    }
}
