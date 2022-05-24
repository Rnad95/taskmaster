package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.State;
import com.amplifyframework.datastore.generated.model.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddTask extends AppCompatActivity {
    public static final String TAG = AddTask.class.getSimpleName();
//    private List<Task> allTasks;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent startThirdActivityIntent = new Intent(getApplicationContext(), AllTasks.class);
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
                configureAmplify();

                Spinner taskStateField = findViewById(R.id.state_of_task);
                String taskTitle = taskTitleField.getText().toString();
                String taskBody = taskBodyField.getText().toString();
                String taskState =  taskStateField.getSelectedItem().toString();


                Task task = Task.builder()
                        .title(taskTitle.toString())
                        .description(taskBody.toString())
                        .status(taskState)
                        .build();

//                Task task = new Task(taskTitle,taskBody,taskState);

//                Long newTaskId = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);

                System.out.println("******************** Task Title = " + task.getTitle() + " ************************");
//                List<Task> allTasks=  AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();
//                allTasks.add(task);


                Amplify.API.mutate(ModelMutation.create(task),
                        success -> Log.i(TAG,"Saved item: "+ success.getData().getTitle()),
                        error -> Log.e(TAG,"Could not save to API" + error)
                        );

                Intent intent = new Intent(getApplicationContext(), AllTasks.class);
                Log.i(TAG,"TASK "+ task);

                intent.putExtra("PassingTask", String.valueOf(task));
                setResult(RESULT_OK,intent);
                finish();

            }
        });

    }

    private void configureAmplify(){

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e(TAG, "Could not initialize Amplify", e);
        }
    }
}