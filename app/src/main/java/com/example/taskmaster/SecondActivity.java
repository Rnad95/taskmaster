package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private List<String> tasks;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Intent startThirdActivityIntent = new Intent(getApplicationContext(), ThirdActivity.class);
//            startThirdActivityIntent.putExtra("PassingTest","Test test test");
            startActivity(startThirdActivityIntent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        Button mAddTaskButton2 = findViewById(R.id.add_task_2);
        EditText mInputTheTitle=findViewById(R.id.input_title);
        TextInputEditText mInputTheDescription=findViewById(R.id.description_task);

//        mAddTaskButton2.setOnClickListener(mClickListener);

        mAddTaskButton2.setOnClickListener(view -> {
            Toast.makeText(this, "submitted", Toast.LENGTH_SHORT).show();
        });

    }
}