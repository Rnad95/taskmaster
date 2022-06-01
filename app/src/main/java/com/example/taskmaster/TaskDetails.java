package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class TaskDetails extends AppCompatActivity {

    public static final String TAG = TaskDetails.class.getSimpleName();
    private ImageView mImageView;
    private ImageView imageView;
    private String url = "https://taskmastercaafb4b555234bf89514dc0176ae513e224250-mastertask.s3.amazonaws.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Bundle bundle = getIntent().getExtras();
        TextView determinedData = findViewById(R.id.task_titlte_details_page);
        TextView stateData = findViewById(R.id.state_view);
        TextView desc = findViewById(R.id.task_details);
        imageView = (ImageView) findViewById(R.id.imageView);

        /**
         * try to display the image
         */
//        downloadImage();
//        imageDisplay();
        Amplify.Storage.getUrl("image.jpg",
                success ->{
                    Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                    Log.i(TAG, "Successfully display: " + success.getUrl());
                    Log.i(TAG, "image Data path => "+ success.getUrl().getPath());
                    Log.i(TAG, "image Data File => "+ success.getUrl().getFile());
                    Log.i(TAG, "image Data Ref => "+ success.getUrl().getRef());
                    try {
                        Log.i(TAG, "image Data URI => "+ success.getUrl().toURI());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

//                    Picasso.get().load(url+success.getUrl().getPath()).into(imageView);
                    url = url+""+success.getUrl().getPath();

                },
                error -> Log.e(TAG,  "display Failure", error)
        );

        String task = bundle.getString("SpecificData");
        String state = bundle.getString("stateData");
        String description = bundle.getString("bodyData");

        Log.i(TAG,"url =>" + url);
//        Picasso.get().load("").into(imageView);

        determinedData.setText(task);
        stateData.setText(state);
        desc.setText(description);


    }


    private void imageDisplay() {
        Amplify.Storage.getUrl("image.jpg",
                success ->{
                    Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                    Log.i(TAG, "Successfully display: " + success.getUrl());
                    Glide.with(this).load(getApplicationContext().getFilesDir().toURI()).into(imageView);
                    },
                error -> Log.e(TAG,  "display Failure", error)
        );

    }

    private String downloadImage() {
        Amplify.Storage.downloadFile(
                "image.jpg",
                new File(getApplicationContext().getFilesDir() + "/download.jpg"),
                result -> {
                    Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                    Log.i(TAG, "Successfully downloaded: " + result.getFile().getName().toString());
                },
                error -> Log.e(TAG,  "Download Failure", error)
        );
        return ""+getApplicationContext().getFilesDir();
    }
}