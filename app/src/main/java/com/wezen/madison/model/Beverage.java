package com.wezen.madison.model;

/**
 * Created by eder on 4/13/15.
 */
public class Beverage {

    private String name;
    private String price;
    private Integer imageResId;


    public Beverage(){}

    public Beverage(String name, String price, Integer imageResId){
        setName(name);
        setPrice(price);
        setImageResId(imageResId);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(Integer imageResId) {
        this.imageResId = imageResId;
    }


}
