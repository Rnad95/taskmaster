package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.geo.maplibre.view.AmplifyMapView;

import com.amplifyframework.geo.models.Coordinates;
import com.amplifyframework.geo.models.CountryCode;
import com.amplifyframework.geo.models.MapStyle;
import com.amplifyframework.geo.models.SearchArea;
import com.amplifyframework.geo.options.GeoSearchByTextOptions;
import com.amplifyframework.predictions.models.LanguageType;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collections;

public class TaskDetails extends AppCompatActivity {

    public static final String TAG = TaskDetails.class.getSimpleName();
    private ImageView mImageView;
    private ImageView imageView;
    private FusedLocationProviderClient mFusedLocationClient;

    private AmplifyMapView mapView;
    private Button mTranslateBtn;
    private String description;
    private TextView translateData;
    private final MediaPlayer mp = new MediaPlayer();
    private Button mSpeechBtn;
    private Uri uri;
    private String imageKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
//        translateData.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        double lat = bundle.getDouble("latitude");
        double longi = bundle.getDouble("longitude");
        imageKey = bundle.getString("imageKey");

        TextView determinedData = findViewById(R.id.task_titlte_details_page);
        TextView stateData = findViewById(R.id.state_view);
        TextView desc = findViewById(R.id.task_details);
        imageView = (ImageView) findViewById(R.id.imageView);
        mTranslateBtn = findViewById(R.id.btn_translate);
        translateData = findViewById(R.id.text_view_translate);
        mSpeechBtn = findViewById(R.id.btn_sound);

        mTranslateBtn.setOnClickListener(view ->{
            textTranslate();
        });
        mSpeechBtn.setOnClickListener(view ->{
            textToSpeech();
        });


        /**
         * Add the Map
         * Doc's
         */
        mapView = findViewById(R.id.mapView);
        Coordinates position = new Coordinates(lat,longi);
        mapView.setOnPlaceSelectListener((place, symbol) -> {
            // place is an instance of AmazonLocationPlace
            // symbol is an instance of Symbol from MapLibre
            Log.i("MyAmplifyApp", "The selected place is " + place.getLabel());
            Toast.makeText(this, "The selected place is " + place.getLabel(), Toast.LENGTH_SHORT).show();
            Log.i("MyAmplifyApp", "It is located at " + place.getCoordinates());
        });


        /**
         * try to display the image
         */
//        Amplify.Storage.getUrl("image.jpg",
//                success ->{
//                    Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
////                    Log.i(TAG, "Successfully display: " + success.getUrl());
//                    Log.i(TAG, "image Data path => "+ success.getUrl().getPath());
//                    Log.i(TAG, "image Data File => "+ success.getUrl().getFile());
//                    try {
//                        Log.i(TAG, "Success ");
//                        uri = Uri.parse( success.getUrl().toURI().toString() );
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    }
//                },
//                error -> Log.e(TAG,  "display1 Failure", error)
//        );
        File file =new File(downloadImage()+"/"+imageKey+".jpg");

        Picasso.get()
                .load(file)
                .into(imageView);


        String task = bundle.getString("SpecificData");
        String state = bundle.getString("stateData");
        description = bundle.getString("bodyData");

//        Log.i(TAG,"url =>" + url);
//        Picasso.get().load("").into(imageView);

        determinedData.setText(task);
        stateData.setText(state);
        desc.setText(description);

    }

    private void textToSpeech() {
        Amplify.Predictions.convertTextToSpeech(
                description,
                result -> playAudio(result.getAudioData()),
                error -> Log.e("MyAmplifyApp", "Conversion failed", error)
        );
    }
    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }
    }
    private void textTranslate() {

        Amplify.Predictions.translateText(
                description,
                LanguageType.ENGLISH,
                LanguageType.ARABIC,
                result ->{
                    Log.i(TAG, result.getTranslatedText());
                    translateData.setText( result.getTranslatedText());
                    },
                error -> Log.e(TAG, "Translation failed", error)
        );
    }

    /**
     * Display image stuff
     */

    private String downloadImage() {

        Amplify.Storage.downloadFile(
                imageKey,
                new File(getApplication().getFilesDir(),"/"+imageKey+".jpg"),
                success -> {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(success.getFile().getPath()));
                },
                error -> Log.e(TAG,  "Download Failure", error)
        );
        return ""+getApplicationContext().getFilesDir();
    }

}