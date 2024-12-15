package com.example.codeit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.codeit.databinding.ActivityProfileViewBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileView extends AppCompatActivity {
FirebaseDatabase database;
ActivityProfileViewBinding binding;
FirebaseAuth auth;
String mail;
String name;
String uid;
String profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityProfileViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        uid = auth.getUid();
        database.getReference("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("name").getValue(String.class);
                profile = snapshot.child("photoUrl").getValue(String.class);
                mail = snapshot.child("email").getValue(String.class);
                binding.name.setText(name);
                binding.email.setText(mail);
                Glide.with(ProfileView.this).load(profile).into(binding.profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.name.getText().toString();
                if(name.isEmpty()){
                    binding.name.setError("This field cannot be empty");
                }
                else{
                    database.getReference("users").child(uid).child("name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(ProfileView.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }
}