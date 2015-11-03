package com.wezen.madison.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by eder on 15/09/2015.
 */
public class HomeServiceRequest {

    private String image;
    private String name;
    private String description;
    private int review;
    private String date;
    private HomeServiceRequestStatus status;
    private LatLng userLocation;
    private String homeServiceRequestID;
    private Boolean wasRated;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public HomeServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(HomeServiceRequestStatus status) {
        this.status = status;
    }

    public LatLng getLocation() {
        return userLocation;
    }

    public void setLocation(LatLng location) {
        this.userLocation = location;
    }

    public String getHomeServiceRequestID() {
        return homeServiceRequestID;
    }

    public void setHomeServiceRequestID(String homeServiceRequestID) {
        this.homeServiceRequestID = homeServiceRequestID;
    }

    public Boolean getWasRated() {
        return wasRated;
    }

    public void setWasRated(Boolean wasRated) {
        this.wasRated = wasRated;
    }
}
