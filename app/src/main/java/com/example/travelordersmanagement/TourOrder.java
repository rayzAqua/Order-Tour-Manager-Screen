package com.example.travelordersmanagement;

public class TourOrder {
    // Main panel
    private int resourceId;
    private String tourName;
    private String rating;
    private String date;
    private String cost;
    private String place;
    private String status;

    // Expand panel
    private boolean expandable;
    private String tourId;
    private String customer_name;
    private int customerImg;
    private String phoneNumber;
    private String email;
    private String peoples;
    private String totalCost;
    private String note;

    public TourOrder(int resourceId, String tourName, String rating, String date,
                     String cost, String place, String status, String tourId,
                     String customer_name, int customerImg, String phoneNumber,
                     String email, String peoples, String totalCost, String note) {
        this.resourceId = resourceId;
        this.tourName = tourName;
        this.rating = rating;
        this.date = date;
        this.cost = cost;
        this.place = place;
        this.status = status;

        this.expandable = false;
        this.tourId = tourId;
        this.customer_name = customer_name;
        this.customerImg = customerImg;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.peoples = peoples;
        this.totalCost = totalCost;
        this.note = note;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getTourName() {
        return tourName;
    }

    public String getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public String getCost() {
        return cost;
    }

    public String getPlace() {
        return place;
    }

    public String getStatus() {
        return status;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public String getTourId() {
        return tourId;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public int getCustomerImg() {
        return customerImg;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPeoples() {
        return peoples;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public String getNote() {
        return note;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setCustomerImg(int customerImg) {
        this.customerImg = customerImg;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPeoples(String peoples) {
        this.peoples = peoples;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
