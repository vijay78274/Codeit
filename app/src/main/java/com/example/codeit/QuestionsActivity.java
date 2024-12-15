package com.example.codeit;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.codeit.Model.Question;
import com.example.codeit.Networking.ApiClient;
import com.example.codeit.Views.FullScreenImage;
import com.example.codeit.databinding.ActivityQuestionsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QuestionsActivity extends AppCompatActivity {
DatabaseReference databaseReference;
ActivityQuestionsBinding binding;
String image;
String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Once data is loaded, stop the shimmer and show real content
                binding.shimmerLayout.stopShimmer();
                binding.shimmerLayout.setVisibility(View.GONE);

                // Display real content here (e.g., RecyclerView, TextViews, etc.)
                binding.content.setVisibility(View.VISIBLE);
            }
        }, 3000);
        TextView questionText = findViewById(R.id.questionText);
        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
        Log.d("selectedDate: ",selectedDate);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Question");
        // Fetch question for the selected date
        if (selectedDate != null) {
            databaseReference.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Question question = dataSnapshot.getValue(Question.class);
//                        String myquestion = dataSnapshot.child("questionText").getValue(String.class);
                        if (question != null) {
                            image=question.getImage();
                            questionText.setText(question.getQuestionText());
                            binding.title.setText(question.getTitle());
                            Glide.with(QuestionsActivity.this).load(question.getImage()).into(binding.imageView);
                        }
                    } else {
                        // Handle case where no question exists for the selected date
                        questionText.setText("No question for this date.");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }
        });
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreenImage dialogFragment = FullScreenImage.newInstance(image);
                dialogFragment.show(getSupportFragmentManager(), "FullScreenImageDialog");
            }
        });
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = binding.link.getText().toString();
                if(str.isEmpty()){
                    binding.link.setError("Link cannot be empty");
                }
                else{
                    ApiClient.sendStringToServer(str, new ApiClient.ApiCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonResponse) {
                            try {
                                // Parse the response data
                                boolean substring1 = jsonResponse.getBoolean("substring1");
                                boolean substring2 = jsonResponse.getBoolean("substring2");
                                boolean substring3 = jsonResponse.getBoolean("substring3");

                                // Use the results (for example, display them in a TextView)
                                runOnUiThread(() -> {
                                    binding.testLayout.setVisibility(View.VISIBLE);
                                    if(substring1){
                                        binding.img1.setImageResource(R.drawable.correct);
                                        binding.text1.setText("Test 1 passed");
                                    }
                                    else{
                                        binding.img1.setImageResource(R.drawable.cancel);
                                        binding.text1.setText("Test 1 failed");
                                    }
                                    if(substring2){
                                        binding.img2.setImageResource(R.drawable.correct);
                                        binding.text2.setText("Test 2 passed");
                                    }
                                    else{
                                        binding.img2.setImageResource(R.drawable.cancel);
                                        binding.text2.setText("Test 2 failed");
                                    }
                                    if(substring3){
                                        binding.img3.setImageResource(R.drawable.correct);
                                        binding.text3.setText("Test 3 passed");
                                    }
                                    else{
                                        binding.img3.setImageResource(R.drawable.cancel);
                                        binding.text3.setText("Test 3 failed");
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            // Handle error (for example, show a Toast)
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                }
            }
        });
    }
}