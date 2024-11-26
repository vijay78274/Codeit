package com.example.codeit.Model;

public class Question {
    private String date; // Use a string format like "yyyy-MM-dd"
    private String questionText;

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

