package com.example.codeit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeit.Model.Question;
import com.example.codeit.Model.QuestionsActivity;
import com.example.codeit.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
FirebaseAuth auth;
private ItemAdapter itemAdapter;
FirebaseDatabase database;
private List<ItemModel> itemList;
    Map<String, Question> questionMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        AndroidThreeTen.init(this);
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        questionMap = new HashMap<>();
        initializeQuestions();
        binding.calendarView.setSelectedDate(CalendarDay.today());
        binding.calendarView.addDecorator(new TodayDecorator(this));
        binding.calendarView.setOnDateChangedListener((widget, date, selected) -> {
//            String selectedDate = date.getDate().toString(); // Format: yyyy-MM-dd
            String selectedDate = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
            Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
            intent.putExtra("SELECTED_DATE", selectedDate);
            startActivity(intent);
//            if (questionMap.containsKey(selectedDate)) {
//                Question selectedQuestion = questionMap.get(selectedDate);
//
//                // Navigate to QuestionActivity
//                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
//                intent.putExtra("question", selectedQuestion.getQuestionText());
//                startActivity(intent);
//            } else {
//                Toast.makeText(MainActivity.this, "No question for this date.", Toast.LENGTH_SHORT).show();
//            }
        });

        itemList = new ArrayList<>();
        itemList.add(new ItemModel("SpringBoot",R.drawable.springboot));
        itemList.add(new ItemModel("React",R.drawable.reactjs));
        itemList.add(new ItemModel("HTML",R.drawable.html));
        itemList.add(new ItemModel("CSS",R.drawable.css));
        itemList.add(new ItemModel("Nodejs",R.drawable.nodejs));
        itemList.add(new ItemModel("Django",R.drawable.django));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.recycler.setLayoutManager(gridLayoutManager);
        itemAdapter = new ItemAdapter(this,itemList);
        binding.recycler.setAdapter(itemAdapter);
//        database.getReference("courseImages").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot datasnapshot: snapshot.getChildren()){
//                    ItemModel item = datasnapshot.getValue(ItemModel.class);
//                    itemList.add(item);
//                    itemAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                auth.signOut();
//                Intent intent = new Intent(MainActivity.this,OtpActivity.class);
//                startActivity(intent);
//            }
//        });
//        binding.send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String repoUrl = binding.link.getText().toString();
//                if (!repoUrl.isEmpty()) {
//                    // Call the NetworkUtils method to submit the repository URL
//                    NetworkUtils.submitRepository(repoUrl, new Callback() {
//                        @Override
//                        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
//                            Log.e("NetworkError", "Submission failed", e); // Logs the error
//                            runOnUiThread(() -> {
//                                Toast.makeText(MainActivity.this, "Submission failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            });
//                        }
//
//                        @Override
//                        public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
//                            if (response.isSuccessful()) {
//                                runOnUiThread(() -> {
//                                    Toast.makeText(MainActivity.this, "Repository submitted successfully", Toast.LENGTH_SHORT).show();
//                                    binding.result.setText("Test results pending...");
//                                    binding.result.setVisibility(View.VISIBLE);
//                                });
//                            } else {
//                                Log.e("ResponseError", "Error Code: " + response.code());
//                                Log.e("ResponseError", "Error Message: " + response.message());
//
//                                if (response.body() != null) {
//                                    Log.e("ResponseError", "Error Body: " + response.body().string());
//                                }
//
//                                // Display error in the UI
//                                runOnUiThread(() -> {
//                                    String errorMessage = "Error: " + response.code() + " - " + response.message();
//                                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//                                });
//                            }
//                        }
//                    });
//                } else {
//                    Toast.makeText(MainActivity.this, "Please enter a repository URL", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
    private void initializeQuestions() {
        questionMap.put("2024-11-25", new Question("2024-11-25", "What is Java?"));
        questionMap.put("2024-11-26", new Question("2024-11-26", "Explain OOP concepts."));
        // Add more questions for other dates
    }
}