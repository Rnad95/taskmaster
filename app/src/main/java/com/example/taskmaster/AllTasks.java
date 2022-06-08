package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;

public class AllTasks extends AppCompatActivity implements TaskAdapter.OnTaskListner {
    private static final String TAG = AllTasks.class.getSimpleName();
    Handler handler;
    TaskAdapter adapter;
    RecyclerView allTaskRecyclerView;
    ArrayList<Task> allTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third3);

        handler = new Handler(Looper.getMainLooper(), msg -> {
            allTaskRecyclerView = findViewById(R.id.show_recycler_view);
            allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new TaskAdapter(
                    allTasks, position -> {
                Toast.makeText(AllTasks.this, "The Title of Task => " + allTasks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, TaskDetails.class);
//                intent.putExtra("Task Id => ", allTasks.get(position).getId());
                intent.putExtra("SpecificData",allTasks.get(position).getTitle());
                intent.putExtra("stateData",allTasks.get(position).getStatus());
                intent.putExtra("bodyData",allTasks.get(position).getDescription());
                intent.putExtra("latitude",this.allTasks.get(position).getLatitude());
                intent.putExtra("longitude",this.allTasks.get(position).getLonitude());
                intent.putExtra("imageKey",allTasks.get(position).getImageKey());
                startActivity(intent);
            });
            allTaskRecyclerView.setAdapter(adapter);
            return true;
        });

        Button back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getTasks();

    }

    private void getTasks() {

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    allTasks = new ArrayList<>();

                    if (success.hasData()) {
                        for (Task task : success.getData()) {
                            allTasks.add(task);
                        }
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
                error -> Log.e(TAG, error.toString(), error)
        );

    }

    @Override
    public void onTaskListener(int position) {
        Intent intent = new Intent(getApplicationContext(), TaskDetails.class);
        System.out.println("***************** POSITION ==>"+ position);
        intent.putExtra("SpecificData",allTasks.get(position).getTitle());
        intent.putExtra("stateData",allTasks.get(position).getStatus());
        intent.putExtra("bodyData",allTasks.get(position).getDescription());

        startActivity(intent);
    }
}