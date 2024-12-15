package com.example.codeit.Model;

public class ItemModel {
    private String course;
    private String imageUrl;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    private int resId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ItemModel(String course,int resId) {
        this.course = course;
        this.resId = resId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    public ItemModel(){

    }
}

