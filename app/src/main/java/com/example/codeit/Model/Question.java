package com.example.codeit.Model;

public class Question {
    private String date; // Use a string format like "yyyy-MM-dd"
    private String questionText;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
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

    private String image;
    public Question(String date, String questionText) {
        this.date = date;
        this.questionText = questionText;
    }
    public Question(){}
    public String getDate() {
        return date;
    }

    public String getQuestionText() {
        return questionText;
    }
}

