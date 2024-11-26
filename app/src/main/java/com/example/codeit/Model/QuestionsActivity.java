package com.example.codeit.Model;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codeit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionsActivity extends AppCompatActivity {
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView questionText = findViewById(R.id.questionText);
        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
        Log.d("selectedDate: ",selectedDate);

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
                            questionText.setText(question.getQuestionText());
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
            // Get the question from the intent
//            String question = getIntent().getStringExtra("question");
//
//            // Display the question
//            if (question != null) {
//                questionText.setText(question);
//            } else {
//                questionText.setText("No question available.");
//            }
        }
    }
}