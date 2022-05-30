package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddTask extends AppCompatActivity {
    public static final String TAG = AddTask.class.getSimpleName();
    private List<Task> allTasks;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent startThirdActivityIntent = new Intent(getApplicationContext(), AllTasks.class);
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

                Spinner taskStateField = findViewById(R.id.state_of_task);
                Spinner taskTeamField = findViewById(R.id.team_of_task_setting_page);

                String taskTitle = taskTitleField.getText().toString();
                String taskBody = taskBodyField.getText().toString();
                String taskState =  taskStateField.getSelectedItem().toString();
                String taskTeam =  taskTeamField.getSelectedItem().toString();



                Amplify.API.query(ModelQuery.list(Team.class, Team.NAME.eq(taskTeam)),

                        success -> {
                            Log.i(TAG, "Success");

                            for (Team loop : success.getData()) {
                                if(loop.getName().equals(taskTeam)){
                                    System.out.println("****************************** LOOP NAME"+ loop.getName());
                                     Task task = Task.builder()
                                            .title(taskTitle)
                                            .description(taskBody)
                                            .status(taskState)
                                            .teamTasksId(loop.getId().toString())
                                            .build();

                                    Amplify.DataStore.save(task,
                                            taskSuccess -> Log.i(TAG, "Saved taskOfficial: " + taskSuccess.item().getTitle()),
                                            error -> Log.e(TAG, "Could not save item to DataStore", error)
                                    );

                                    Amplify.API.mutate(ModelMutation.create(task),
                                            successSaveToAPI -> Log.i(TAG,"Saved item: "+ successSaveToAPI.getData().getTitle()),
                                            error -> Log.e(TAG,"Could not save to API" + error)
                                    );

//                                    com.example.taskmaster.Task taskMaster = new com.example.taskmaster.Task(task.getTitle(),task.getDescription(),task.getStatus());
//                                    Long newTaskId = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(taskMaster);
//                                    System.out.println("******************** Task ID = " + newTaskId + " ************************");
//                                    List<com.example.taskmaster.Task> allTasks=  AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();


                                    Intent intent = new Intent(getApplicationContext(), AllTasks.class);
//                                    intent.putExtra("PassingTask", task.toString());
                                    setResult(RESULT_OK,intent);
                                }
                            }
                        },
                        error -> Log.e(TAG, "Error", error)
                );


                Amplify.DataStore.observe(Task.class,
                        started -> {
                            Log.i(TAG, "Observation began.");
                            // TODO: 5/17/22 Update the UI thread with in this call method
                            // Manipulate your views

                            // call handler
                        },
                        change -> Log.i(TAG, change.item().toString()),
                        failure -> Log.e(TAG, "Observation failed.", failure),
                        () -> Log.i(TAG, "Observation complete.")
                );


                finish();
            }
        });

    }

}