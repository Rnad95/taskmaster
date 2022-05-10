package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity implements TaskAdapter.onTaskListner{
    ArrayList<Task> taskData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third3);




        taskData.add(new Task("Reading","Read Your Book",State.PROGRESS));
//        taskData.add(new Task("Coffee","Take Your Break and breath",State.COMPLETE));

        // get the Recycler view
        RecyclerView allTaskRecyclerView = findViewById(R.id.show_recycler_view);

        // set a layout manager
        allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set the adapter for this recycler view
//        allTaskRecyclerView.setAdapter(new TaskAdapter(taskData));

        Bundle bundle = getIntent().getExtras();
        Task task  = (Task) getIntent().getSerializableExtra("PassingTask");
        ArrayList<Task> allTasks= (ArrayList<Task>) AppDatabase.getInstance(getApplicationContext()).studentDao().getAll();
        allTaskRecyclerView.setAdapter(new TaskAdapter(allTasks));


    }

    @Override
    public void onTaskListener(int position) {
        taskData.get(position);
        Intent intent = new Intent(this, TaskDetails.class);
        startActivity(intent);
    }
}