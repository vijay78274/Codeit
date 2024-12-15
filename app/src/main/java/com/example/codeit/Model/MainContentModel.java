package com.example.codeit.Model;

import java.util.List;

public class MainContentModel {
    String course;
    String pre;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    boolean status;
//    List<String> steps;

//    public List<String> getSteps() {
//        return steps;
//    }
//
//    public void setSteps(List<String> steps) {
//        this.steps = steps;
//    }

    String title;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    String imageUrl;
}
