package com.example.codeit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codeit.databinding.ActivityOtpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;


public class OtpActivity extends AppCompatActivity {
    private ActivityOtpBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.LogInView.setVisibility(View.INVISIBLE);
                binding.SignUpView.setVisibility(View.VISIBLE);
            }
        });
        binding.login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.SignUpView.setVisibility(View.INVISIBLE);
                binding.LogInView.setVisibility(View.VISIBLE);
            }
        });
        // GitHub OAuth URL setup
        binding.button.setOnClickListener(v -> {
            OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
            provider.addCustomParameter("prompt", "consent");
            auth.startActivityForSignInWithProvider(this, provider.build())
                    .addOnSuccessListener(authResult -> {
                        // Successfully signed in
                        FirebaseUser user = authResult.getUser();
                        if (user != null) {
                            // Get basic user info
                            String uid = user.getUid();            // User ID
                            String name = user.getDisplayName();    // Display name
                            String email = user.getEmail();         // Email address
                            Uri photoUrl = user.getPhotoUrl();      // Profile photo URL (if available)

                            // Print or use the data
                            Log.d("GitHubUser", "User ID: " + uid);
                            Log.d("GitHubUser", "Name: " + name);
                            Log.d("GitHubUser", "Email: " + email);
                            if (photoUrl != null) {
                                Log.d("GitHubUser", "Photo URL: " + photoUrl.toString());
                            }
                            Intent intent = new Intent(OtpActivity.this,Profile.class);
                            intent.putExtra("name",name);
                            intent.putExtra("email",email);
                            intent.putExtra("photoUrl",photoUrl.toString());
                            startActivity(intent);
                        }
                        // Handle signed-in user info here
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Log.e("GitHubAuth", "GitHub sign-in failed", e);
                    });
        });
    }

}
