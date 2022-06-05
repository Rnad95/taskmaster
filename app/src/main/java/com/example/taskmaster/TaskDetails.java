package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.geo.maplibre.view.AmplifyMapView;

import com.amplifyframework.geo.models.Coordinates;
import com.amplifyframework.geo.models.CountryCode;
import com.amplifyframework.geo.models.MapStyle;
import com.amplifyframework.geo.models.Place;
import com.amplifyframework.geo.models.SearchArea;
import com.amplifyframework.geo.options.GeoSearchByCoordinatesOptions;
import com.amplifyframework.geo.options.GeoSearchByTextOptions;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

public class TaskDetails extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = TaskDetails.class.getSimpleName();
    private ImageView mImageView;
    private ImageView imageView;
    private FusedLocationProviderClient mFusedLocationClient;

    private int PERMISSION_ID = 44;

    private double latitude;
    private double longitude;

    private GoogleMap googleMap;
//    private String url = "https://taskmastercaafb4b555234bf89514dc0176ae513e224250-mastertask.s3.amazonaws.com";

    private AmplifyMapView amplifyMapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Bundle bundle = getIntent().getExtras();
        TextView determinedData = findViewById(R.id.task_titlte_details_page);
        TextView stateData = findViewById(R.id.state_view);
        TextView desc = findViewById(R.id.task_details);
        imageView = (ImageView) findViewById(R.id.imageView);
//        /**
//         *
//         */
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        /**
         * Add the Map
         * Doc's
         */

        amplifyMapView = findViewById(R.id.mapView);

        amplifyMapView.setOnPlaceSelectListener((place, symbol) -> {
            Log.i("MyAmplifyApp", "The selected place is " + place.getLabel());
            Log.i("MyAmplifyApp", "It is located at " + place.getCoordinates());
        });

        String searchQuery = "Jordan";
        Amplify.Geo.searchByText(searchQuery,
                result -> {
                    for (final Place place : result.getPlaces()) {
                        Log.i("MyAmplifyApp", "Place => "+place.toString());
                    }
                },
                error -> Log.e("MyAmplifyApp", "Failed to search for " + searchQuery, error)
        );

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
//                    url = url+""+success.getUrl().getPath();

                },
                error -> Log.e(TAG,  "display Failure", error)
        );

        String task = bundle.getString("SpecificData");
        String state = bundle.getString("stateData");
        String description = bundle.getString("bodyData");

//        Log.i(TAG,"url =>" + url);
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}