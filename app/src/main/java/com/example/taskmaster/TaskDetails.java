package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Bundle bundle = getIntent().getExtras();
        TextView allTasks = findViewById(R.id.task_titlte_details_page);
        String task = bundle.getString("PassingTest");
        allTasks.setText(task);



    }





}