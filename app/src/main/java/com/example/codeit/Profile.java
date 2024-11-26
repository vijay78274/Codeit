package com.example.codeit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.codeit.Model.userModel;
import com.example.codeit.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

public class Profile extends AppCompatActivity {
    ActivityProfileBinding binding;
String name;
String photoUrl;
String email;
FirebaseDatabase database;
FirebaseAuth auth;
String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        database=FirebaseDatabase.getInstance();
        name = getIntent().getStringExtra("name");
        photoUrl = getIntent().getStringExtra("photoUrl");
        email = getIntent().getStringExtra("email");
        auth=FirebaseAuth.getInstance();
        uid=auth.getUid();
        Log.d("imageUrl",photoUrl);
        Glide.with(this).load(photoUrl).placeholder(R.drawable.user).circleCrop().into(binding.image);
        binding.name.setText(name);
        binding.email.setText(email);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile(uid,name,email,photoUrl);
            }
        });
    }
    public void createProfile(String uid, String name, String email, String photoUrl){
        userModel model = new userModel(name,email,photoUrl);
        database.getReference("users").child(uid).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("database","profile stored successfully");
                    Intent intent = new Intent(Profile.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Log.d("error",task.getException().toString());
                }
            }
        });
    }
}