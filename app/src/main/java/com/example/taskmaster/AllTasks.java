package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class AllTasks extends AppCompatActivity implements TaskAdapter.OnTaskListner {
    ArrayList<Task> allTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third3);

        // get the Recycler view
        RecyclerView allTaskRecyclerView = findViewById(R.id.show_recycler_view);
        Button back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        });


        // set a layout manager
        allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        Task task  = (Task) getIntent().getSerializableExtra("PassingTask");
        allTasks= (ArrayList<Task>) AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();
        allTaskRecyclerView.setAdapter(new TaskAdapter(allTasks,this));

    }

    @Override
    public void onTaskListener(int position) {
        Intent intent = new Intent(getApplicationContext(), TaskDetails.class);
        System.out.println("***************** POSITION ==>"+ position);
        intent.putExtra("SpecificData",allTasks.get(position).getTitle());
        intent.putExtra("stateData",allTasks.get(position).getState().toString());
        intent.putExtra("bodyData",allTasks.get(position).getBody());
        startActivity(intent);
    }
}