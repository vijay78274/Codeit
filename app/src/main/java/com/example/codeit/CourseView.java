package com.example.codeit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.codeit.Model.ContentModel;
import com.example.codeit.databinding.ActivityCourseViewBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseView extends AppCompatActivity {
    ActivityCourseViewBinding binding;
    private ContentAdapter contentAdapter;
    FirebaseDatabase database;
    private List<ContentModel> itemList;
    String course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCourseViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        course = getIntent().getStringExtra("course");
        itemList = new ArrayList<>();
        database.getReference("Contents").child(course).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    ContentModel content = dataSnapshot.getValue(ContentModel.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String count = String.valueOf(dataSnapshot.child("count").getValue());
                    ContentModel model = new ContentModel(count,title);
                    model.setCourse(course);
                    itemList.add(model);
                    contentAdapter.notifyDataSetChanged();
                }
                // Use contentList in your app (e.g., pass it to RecyclerView Adapter)
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(layoutManager);
        contentAdapter = new ContentAdapter(this,itemList);

        binding.recycler.setAdapter(contentAdapter);
    }
}