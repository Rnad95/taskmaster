package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mUsernameText;
    Handler handler;
    TaskAdapter adapter;
    RecyclerView allTaskRecyclerView;
    List<Task> allTasks;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent startSecondActivityIntent = new Intent(getApplicationContext(), AddTask.class);
            startActivity(startSecondActivityIntent);
        }
    };

    private final View.OnClickListener mClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent startThirdActivityIntent = new Intent(getApplicationContext(), AllTasks.class);
            startActivity(startThirdActivityIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mAddTaskButton = findViewById(R.id.add_task);
        Button mAllTaskButton = findViewById(R.id.all_task);

        mAddTaskButton.setOnClickListener(mClickListener);
        mAllTaskButton.setOnClickListener(mClickListener2);
        mUsernameText = findViewById(R.id.txt_username);


        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e(TAG, "Could not initialize Amplify", e);
        }

         handler = new Handler(Looper.getMainLooper(), msg -> {
         allTaskRecyclerView = findViewById(R.id.show_recycler_view);
        allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

             adapter = new TaskAdapter(
                    allTasks, position -> {
                Intent intent = new Intent(getApplicationContext(), TaskDetails.class);
                 intent.putExtra("SpecificData",allTasks.get(position).getTitle());
                 intent.putExtra("stateData",allTasks.get(position).getStatus());
                 intent.putExtra("bodyData",allTasks.get(position).getDescription());
                startActivity(intent);
            });
             allTaskRecyclerView.setAdapter(adapter);

            return true;
        });


        Button addBtn = findViewById(R.id.add_task);
        addBtn.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent);
        });

    }


    //    @Override
    public void onTaskListener(int position) {
        Intent intent = new Intent(getApplicationContext(), TaskDetails.class);
        System.out.println("***************** POSITION ==>"+ position);
        intent.putExtra("SpecificData",allTasks.get(position).getTitle());
        intent.putExtra("stateData",allTasks.get(position).getStatus());
        intent.putExtra("bodyData",allTasks.get(position).getDescription());

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAddress();
        getTasks();

    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: called");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                navigateToSettings();
                return true;
            case R.id.action_copyright:
                Toast.makeText(this, "Copyright 2022", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToTaskDetails(int taskId) {
        Intent taskDetailsIntent = new Intent(this, TaskDetails.class);

        startActivity(taskDetailsIntent);
    }

    private void navigateToSettings() {
        Intent settingsIntent = new Intent(this, SettingPage.class);
        startActivity(settingsIntent);
    }

    private void setAddress() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsernameText.setText(sharedPreferences.getString(SettingPage.USERNAME, "Username Doesn't Define"));
    }

    private void getTasks() {
            Amplify.DataStore.query(
                 Task.class,
                    success -> {
                        allTasks = new ArrayList<>();

                        while (success.hasNext()) {
                            Task task = success.next();
                                allTasks.add(task);
                        }
                        Log.i(TAG, "Tasks => " + allTasks);
                        Bundle bundle = new Bundle();
                        bundle.putString("tasksList", success.toString());

                        Message message = new Message();
                        message.setData(bundle);

                        handler.sendMessage(message);

                    runOnUiThread(() -> {
                        allTaskRecyclerView.setAdapter(adapter);
                    });
                    },
                    error -> Log.e(TAG, "Error "+error.toString(), error)
            );


    }

}
