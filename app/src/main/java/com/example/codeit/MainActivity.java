package com.example.codeit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.codeit.Adapters.ItemAdapter;
import com.example.codeit.Model.ItemModel;
import com.example.codeit.Model.Question;
import com.example.codeit.Views.CompletedTaskDecorator;
import com.example.codeit.Views.NotCompletedTaskDecorator;
import com.example.codeit.Views.TodayDecorator;
import com.example.codeit.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
FirebaseAuth auth;
private ItemAdapter itemAdapter;
FirebaseDatabase database;
private List<ItemModel> itemList;

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
        setSupportActionBar(binding.toolbar);
        //Shrimmer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Once data is loaded, stop the shimmer and show real content
                binding.shrimmer.stopShimmer();
                binding.shrimmer.setVisibility(View.GONE);

                // Display real content here (e.g., RecyclerView, TextViews, etc.)
                binding.content.setVisibility(View.VISIBLE);
                binding.toolbar.setVisibility(View.VISIBLE);
            }
        }, 3000);

        Calendar calendar = Calendar.getInstance();
        CalendarDay today = CalendarDay.today();;
        binding.calendarView.setSelectedDate(today);
        binding.calendarView.addDecorator(new TodayDecorator(this));
        binding.calendarView.state().edit()
                .setMaximumDate(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
                .commit();
        //daily task done
        HashSet<CalendarDay> completedDates = new HashSet<>();
        HashSet<CalendarDay> notCompleted = new HashSet<>();
        database.getReference("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot: snapshot.getChildren()){
                    Question question = datasnapshot.getValue(Question.class);
                    String[] parts = question.getDate().split("-");
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);
                    if(question.getStatus()) {
                        completedDates.add(CalendarDay.from(year,month,day));
                    }
                    else{
                        notCompleted.add(CalendarDay.from(year,month,day));
                    }
                    itemAdapter.notifyDataSetChanged();
                    binding.calendarView.addDecorator(new NotCompletedTaskDecorator(MainActivity.this, notCompleted));
                    binding.calendarView.addDecorator(new CompletedTaskDecorator(MainActivity.this, completedDates));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        completedDates.add(CalendarDay.from(2024, 11, 25));
//        completedDates.add(CalendarDay.from(2024, 11, 26));
//        notCompleted.add(CalendarDay.from(2024, 11, 19));
//        notCompleted.add(CalendarDay.from(2024, 11, 20));
        //on date click
        binding.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            String selectedDate = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
            Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
            intent.putExtra("SELECTED_DATE", selectedDate);
            startActivity(intent);
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
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        binding.recommendView.setLayoutManager(layoutManager);
//        binding.recommendView.setAdapter(itemAdapter);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.settings){
//            Toast.makeText(MainActivity.this,"Setting clicked",Toast.LENGTH_SHORT).show();
            findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view);
                }
            });
            return true;
        }
        else if(id==R.id.profile){
            Toast.makeText(MainActivity.this,"Profile clicked",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ProfileView.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.pop_menu, popupMenu.getMenu());
        // Set a click listener for menu item clicks
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemid = item.getItemId();
            if(itemid==R.id.action_one){
                Toast.makeText(MainActivity.this,"Action2 clicked",Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(itemid==R.id.action_two){
                Toast.makeText(MainActivity.this,"Action2 clicked",Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(itemid==R.id.action_three){
                auth.signOut();
                Intent intent = new Intent(MainActivity.this,OtpActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }
}