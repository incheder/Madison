package com.wezen.madison.model;

import android.graphics.Bitmap;

/**
 * Created by eder on 15/09/2015.
 */
public class HistoryService {

    private Bitmap image;
    private String name;
    private String description;
    private int review;
    private String date;
    private HomeServiceStatus status;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HomeServiceStatus getStatus() {
        return status;
    }

    public void setStatus(HomeServiceStatus status) {
        this.status = status;
    }
}
