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
        TextView determinedData = findViewById(R.id.task_titlte_details_page);
        TextView stateData = findViewById(R.id.state_view);
        TextView desc = findViewById(R.id.task_details);

        String task = bundle.getString("SpecificData");
        String state = bundle.getString("stateData");
        String description = bundle.getString("bodyData");

        determinedData.setText(task);
        stateData.setText(state);

        desc.setText(description);


    }





}