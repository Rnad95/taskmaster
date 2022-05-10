package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

        mAddTaskButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText taskTitleField = findViewById(R.id.input_title);
                EditText taskBodyField = findViewById(R.id.description_task);
//                EditText taskStateField = findViewById(R.id.state_of_task);
                Spinner taskStateField = findViewById(R.id.state_of_task);
                String taskTitle = taskTitleField.getText().toString();
                String taskBody = taskBodyField.getText().toString();
                String taskState = taskStateField.getSelectedItem().toString();
                Task task = new Task(taskTitle,taskBody,taskState);
//                if(taskState.toUpperCase().equals( State.PROGRESS))
//                     task =new Task(taskTitle,taskBody,State.PROGRESS);
//                else if (taskState.toUpperCase().equals( State.COMPLETE))
//                     task =new Task(taskTitle,taskBody,State.COMPLETE);
//                else if(taskState.toUpperCase().equals( State.ASSIGNED))
//                    task =new Task(taskTitle,taskBody,State.ASSIGNED);
//                else
//                    task =new Task(taskTitle,taskBody,State.NEW);


                Long newTaskId = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);

                System.out.println("******************** Task ID = " + newTaskId + " ************************");
                List<Task> allTasks=  AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();


                Intent intent = new Intent(getApplicationContext(),ThirdActivity.class);
                intent.putExtra("PassingTask",task);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}