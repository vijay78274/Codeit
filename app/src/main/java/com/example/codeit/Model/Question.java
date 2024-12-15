package com.example.codeit.Model;

public class Question {
    private String date;
    private String questionText;
    private boolean status;
    private String title;
    private String image;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Question(String date, String image, String questionText, boolean status, String title) {
        this.date = date;
        this.image = image;
        this.questionText = questionText;
        this.status = status;
        this.title = title;
    }
    public Question(){}
    public String getDate() {
        return date;
    }

    public String getQuestionText() {
        return questionText;
    }
}

