package com.example.codeit.Model;

public class userModel {
    String name, email, photoUrl;
    public userModel(String name, String email, String photoUrl){
        this.name=name;
        this.email=email;
        this.photoUrl=photoUrl;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getPhotoUrl(){
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl){
        this.photoUrl=photoUrl;
    }
}
