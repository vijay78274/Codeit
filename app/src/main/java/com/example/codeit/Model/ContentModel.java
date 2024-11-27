package com.example.codeit.Model;

public class ContentModel {
    String title, count;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    boolean status;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    String course;
    public ContentModel(){

    }
    public ContentModel(String count, String title, boolean status){
        this.count=count;
        this.title=title;
        this.status=status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
