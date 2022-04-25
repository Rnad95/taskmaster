package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third3);

//        Bundle bundle = getIntent().getExtras();
//        TextView allTasks = findViewById(R.id.show_tasks);
//        String task = bundle.getString("PassingTest");
//        allTasks.setText(task+"\n");
    }
}