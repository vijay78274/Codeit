package com.example.codeit.Model;

public class ContentModel {
    String title, count;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    String course;
    public ContentModel(){

    }
    public ContentModel(String count, String title){
        this.count=count;
        this.title=title;
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
