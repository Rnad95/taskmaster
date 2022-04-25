package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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

        //          TRY TO ADD LINK TO CLICK ON IMAGE
//        ImageView logo = findViewById(R.id.logo_id);

        Button mAddTaskButton = findViewById(R.id.add_task);
        Button mAllTaskButton = findViewById(R.id.all_task);

        mAddTaskButton.setOnClickListener(mClickListener);
        mAllTaskButton.setOnClickListener(mClickListener2);

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
        Log.i(TAG, "onResume: called - The App is VISIBLE");
        super.onResume();
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

}