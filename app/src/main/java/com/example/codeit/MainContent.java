package com.example.codeit;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.codeit.Adapters.MainContentAdapter;
import com.example.codeit.Networking.ApiClient;
import com.example.codeit.Model.MainContentModel;
import com.example.codeit.Views.FullScreenImage;
import com.example.codeit.databinding.ActivityMainContentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainContent extends AppCompatActivity {
ActivityMainContentBinding binding;
String title;
List<String> itemList;
FirebaseDatabase database;
MainContentAdapter adapter;
boolean status;
String count;
List<String> countList;
String image;
String str;
String course;
    String pre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        status=false;
        database=FirebaseDatabase.getInstance();
        title = getIntent().getStringExtra("title");
        course=getIntent().getStringExtra("course");
        count=getIntent().getStringExtra("count");
        binding.title.setText(title);
        itemList = new ArrayList<>();
        countList = new ArrayList<>();
        database.getReference("Contents").child(course).child(count).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MainContentModel model = snapshot.getValue(MainContentModel.class);
//                pre = snapshot.getValue(String.class);
                pre=model.getPre();
                image=model.getImageUrl();
//                Log.d("Prerequisit : ",pre);
                binding.pre.setText(pre);
                Glide.with(MainContent.this).load(image).into(binding.imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference("Contents").child(course).child(count).child("steps").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String title = dataSnapshot.child("count").getValue(String.class);
                    itemList.add(title);
                    adapter.notifyDataSetChanged();
                }
                // Use contentList in your app (e.g., pass it to RecyclerView Adapter)
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(int i=0;i<itemList.size();i++){
            countList.add(String.valueOf(i+1));
            Log.d("items: ",itemList.get(i));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(layoutManager);
        adapter = new MainContentAdapter(this,itemList);
        binding.recycler.setAdapter(adapter);

//        binding.submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                str = binding.link.getText().toString();
//                if(str.isEmpty()){
//                    binding.link.setError("Link cannot be empty");
//                }
//                else {
//                    ApiClient.sendStringToServer(str, new ApiClient.ApiCallback() {
//                        @Override
//                        public void onSuccess(JSONObject jsonResponse) {
//                            try {
//                                // Display the results on the UI
//                                StringBuilder result = new StringBuilder();
//                                // Use jsonResponse.keys() to get an iterator of keys
//                                Iterator<String> keys = jsonResponse.keys();
//                                // Iterate through each key in the JSON response
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    result.append(key).append(": ").append(jsonResponse.getString(key)).append("\n");
//                                }
//                                Log.d("Result",result.toString());
//                                runOnUiThread(() -> {
//                                        binding.testLayout.setVisibility(View.VISIBLE);
//                                        binding.testResult1.setText(result.toString());
//                                        binding.testResult1.setText(result.toString());
//                                        binding.testResult1.setText(result.toString());
//                                    }
//                                );
////                                binding.testLayout.setVisibility(View.VISIBLE);
////                                binding.testResult.setText(result.toString());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        });
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
                                    if(substring2&&substring1&&substring3){
                                        status=true;
                                        database.getReference("Contents").child(course).child(count).child("status").setValue(status);
                                        Log.d("status: ",String.valueOf(status));
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
//        if(status){
//            database.getReference("Contents").child(course).child(count).child("status").setValue(true);
//        }
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreenImage dialogFragment = FullScreenImage.newInstance(image);
                dialogFragment.show(getSupportFragmentManager(), "FullScreenImageDialog");
            }
        });
    }
}