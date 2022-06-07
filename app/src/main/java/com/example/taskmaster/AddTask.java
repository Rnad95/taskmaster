package com.example.taskmaster;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;
import com.vmadalin.easypermissions.EasyPermissions;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class AddTask extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    public static final String TAG = AddTask.class.getSimpleName();
    public static final int REQUEST_CODE = 123;
    private List<Task> allTasks;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent startThirdActivityIntent = new Intent(getApplicationContext(), AllTasks.class);
            startActivity(startThirdActivityIntent);

        }
    };
    private Button mAddTaskButton2;
    private EditText mInputTheTitle;
    private Button mUploadImageButton;
    private File file;
    private String key;
    private Intent intent;
    private ImageView mImageView;
    private static FusedLocationProviderClient fusedLocationProviderClient;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        mAddTaskButton2 = findViewById(R.id.add_task_2);
        mUploadImageButton = findViewById(R.id.upload_image);
        mInputTheTitle = findViewById(R.id.input_title);
        TextInputEditText mInputTheDescription = findViewById(R.id.description_task);
        mImageView = findViewById(R.id.image_view);


        Intent intent1 = getIntent();
        intent1.setType(intent1.ACTION_SEND);
        if (intent1 != null && intent1.getType() != null || intent1.getData() != null) {
            Uri imageUri = (Uri) intent1.getParcelableExtra(Intent.EXTRA_STREAM);
            Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
            mImageView.setImageURI(imageUri);
        }
        mUploadImageButton.setOnClickListener(view -> {
            uploadPhoto();
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AddTask.this);

        mAddTaskButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText taskTitleField = findViewById(R.id.input_title);
                EditText taskBodyField = findViewById(R.id.description_task);
                Spinner taskStateField = findViewById(R.id.state_of_task);
                Spinner taskTeamField = findViewById(R.id.team_of_task_setting_page);

                String taskTitle = taskTitleField.getText().toString();
                String taskBody = taskBodyField.getText().toString();
                String taskState = taskStateField.getSelectedItem().toString();
                String taskTeam = taskTeamField.getSelectedItem().toString();

                if (hasLocationPermission()) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List currLocation = geocoder.getFromLocation(location.getLatitude(),
                                    location.getLongitude(),
                                    1);
                            Log.i(TAG, "CurrentLocation => " + currLocation);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(AddTask.this, "Latitude => " + location.getLatitude(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddTask.this, "Longitude => " + location.getLongitude(), Toast.LENGTH_SHORT).show();

                        latitude = ((float) location.getLatitude());
                        longitude = ((float) location.getLongitude());


                    });
                } else {
                    requestLocationPermission();
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                        Log.i(TAG, "" + location.getLongitude());
                        Toast.makeText(getApplicationContext(), "NO LONGITUDE & LATITUDE =>" + location.getLongitude(), Toast.LENGTH_SHORT).show();

                    });
                }

//                    // upload to s3
//                    // uploads the file
                Amplify.Storage.uploadFile(
                        taskTitle + ".jpg",
                        file,
                        uploadSuccess -> {
                            Log.i(TAG, "upload image is succeed" + uploadSuccess.getKey());
                            runOnUiThread(() -> {
                                key = uploadSuccess.getKey();
                                Amplify.API.query(ModelQuery.list(Team.class, Team.NAME.eq(taskTeam)),
                                        success -> {
                                            Log.i(TAG, "Success");
                                            for (Team loop : success.getData()) {
                                                if (loop.getName().equals(taskTeam)) {
                                                    System.out.println("****************************** LOOP NAME" + loop.getName());
                                                    Task task = Task.builder()
                                                            .title(taskTitle)
                                                            .description(taskBody)
                                                            .status(taskState)
                                                            .lonitude(longitude)
                                                            .latitude(latitude)
                                                            .teamTasksId(loop.getId().toString())
                                                            .build();

                                                    Amplify.DataStore.save(task,
                                                            taskSuccess -> Log.i(TAG, "Saved taskOfficial: " + taskSuccess.item().getTitle()),
                                                            error -> Log.e(TAG, "Could not save item to DataStore", error)
                                                    );

                                                    Amplify.API.mutate(ModelMutation.create(task),
                                                            successSaveToAPI -> Log.i(TAG, "Saved item: " + successSaveToAPI.getData().getTitle()),
                                                            error -> Log.e(TAG, "Could not save to API" + error)
                                                    );


                                                    Intent intent = new Intent(getApplicationContext(), AllTasks.class);
                                                    setResult(RESULT_OK, intent);
                                                }
                                            }
                                        },
                                        error -> Log.e(TAG, "Error", error)
                                );
                            });
                        },
                        failure -> {
                            Log.e(TAG, "Uploaded failed.", failure);

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
                                                        .lonitude(longitude)
                                                        .latitude(latitude)
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

                                                Intent intent = new Intent(getApplicationContext(), AllTasks.class);
                                                setResult(RESULT_OK,intent);
                                            }
                                        }
                                    },
                                    error -> Log.e(TAG, "Error", error)
                            );


                        });

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

    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(
                getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        );

    }

    private void requestLocationPermission() {
        EasyPermissions.requestPermissions(
                this,
                "this Application cannot work without Location Permission",
                REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(REQUEST_CODE, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            Toast.makeText(this, "the application have not permission", Toast.LENGTH_SHORT).show();


        } else {
            requestLocationPermission();
        }
    }

//    @Override
//    public void onPermissionsGranted(int i, @NonNull List<String> list) {
//        Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
//        setViewVisibilty();
//    }
//

    /**
     * Image
     */

    private void uploadPhoto() {

        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
//          startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

    }

    private void displayImage() {
        Amplify.Storage.getUrl("image.jpg",
                success -> {
                    Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                    Log.i(TAG, "Successfully downloaded: " + success.getUrl());

                },
                error -> Log.e(TAG, "Download Failure", error)
        );
//        Amplify.Storage.downloadFile(
//                "image.jpg",
//                new File(getApplicationContext().getFilesDir() + "/download.jpg"),
//                result -> {
//                    Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
//                    Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());
//                },
//                error -> Log.e(TAG,  "Download Failure", error)
//        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            Log.e(TAG, "onActivityResult: Error getting image from device");
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();
                mImageView.setImageURI(currentUri);
                // Do stuff with the photo/video URI.
                Log.i(TAG, "onActivityResult: the uri is => " + currentUri);

                try {
                    Bitmap bitmap = getBitmapFromUri(currentUri);

                    file = new File(getApplicationContext().getFilesDir(), "image.jpg");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}