package com.wezen.madison.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by eder on 15/09/2015.
 */
public class HomeServiceRequest {
    private String id;
    private String image;
    private String name;
    private String description;
    private Integer review;
    private String date;
    private HomeServiceRequestStatus status;
    private LatLng userLocation;
    private String homeServiceRequestID;
    private Boolean wasRated;
    private Integer colorForStatus;
    private String attendedBy;
    private String attendedByAvatar;
    private Date dateForService;
    private Double averageStars;

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

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getColorForStatus() {
        return colorForStatus;
    }

    public void setColorForStatus(Integer colorForStatus) {
        this.colorForStatus = colorForStatus;
    }

    public String getAttendedBy() {
        return attendedBy;
    }

    public void setAttendedBy(String attendedBy) {
        this.attendedBy = attendedBy;
    }

    public String getAttendedByAvatar() {
        return attendedByAvatar;
    }

    public void setAttendedByAvatar(String attendedByAvatar) {
        this.attendedByAvatar = attendedByAvatar;
    }

    public Date getDateForService() {
        return dateForService;
    }

    public void setDateForService(Date dateForService) {
        this.dateForService = dateForService;
    }

    public Double getAverageStars() {
        return averageStars;
    }

    public void setAverageStars(Double averageStars) {
        this.averageStars = averageStars;
    }
}
