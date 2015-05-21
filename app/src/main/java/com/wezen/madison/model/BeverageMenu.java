package com.wezen.madison.model;

/**
 * Created by eder on 5/18/15.
 */
public class BeverageMenu {
    private Integer beverageMenuImage;
    private String beverageMenuName;

    public BeverageMenu(){}

    public BeverageMenu(Integer beverageMenuImage, String beverageMenuName){
        setBeverageMenuImage(beverageMenuImage);
        setBeverageMenuName(beverageMenuName);
    }

    public Integer getBeverageMenuImage() {
        return beverageMenuImage;
    }

    public void setBeverageMenuImage(Integer beverageMenuImage) {
        this.beverageMenuImage = beverageMenuImage;
    }

    public String getBeverageMenuName() {
        return beverageMenuName;
    }

    public void setBeverageMenuName(String beverageMenuName) {
        this.beverageMenuName = beverageMenuName;
    }
}
