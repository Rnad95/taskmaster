package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity implements TaskAdapter.OnTaskListner {
    public ArrayList<Task> taskData = new ArrayList<>();
    ArrayList<Task> allTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third3);


//        taskData.add(new Task("Reading","Read Your Book",State.PROGRESS));
//        taskData.add(new Task("Coffee","Take Your Break and breath",State.COMPLETE));

        // get the Recycler view
        RecyclerView allTaskRecyclerView = findViewById(R.id.show_recycler_view);

        // set a layout manager
        allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set the adapter for this recycler view
//        allTaskRecyclerView.setAdapter(new TaskAdapter(taskData));

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