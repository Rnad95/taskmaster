package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mUsernameText;


    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent startSecondActivityIntent = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(startSecondActivityIntent);
        }
    };

    private final View.OnClickListener mClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent startThirdActivityIntent = new Intent(getApplicationContext(), ThirdActivity.class);
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

        Button task1 = findViewById(R.id.android_task);
        Button task2 = findViewById(R.id.coffee_task);
        Button task3 = findViewById(R.id.design_task);

        task1.setOnClickListener(view -> {
            navigateToTaskDetails(task1.getId());
        });
        task2.setOnClickListener(view -> {
            navigateToTaskDetails(task2.getId());
        });
        task3.setOnClickListener(view -> {
            navigateToTaskDetails(task3.getId());
        });
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
    private String[]  citties ={"",""};

    private void navigateToTaskDetails(int taskId) {
        Intent taskDetailsIntent = new Intent(this, TaskDetails.class);
        switch (taskId) {
            case R.id.android_task:
                taskDetailsIntent.putExtra("PassingTest", "Android Task");
                break;
            case R.id.coffee_task:
                taskDetailsIntent.putExtra("PassingTest", "Coffee Task");
                break;
            case R.id.design_task:
                taskDetailsIntent.putExtra("PassingTest", "Design Task");
                break;
        }
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

}