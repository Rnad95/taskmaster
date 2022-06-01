package com.example.taskmaster;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class AddTask extends AppCompatActivity {
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
    private  File file;
    private String key;
    private Intent intent;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        mAddTaskButton2 = findViewById(R.id.add_task_2);
        mUploadImageButton = findViewById(R.id.upload_image);
        mInputTheTitle = findViewById(R.id.input_title);
        TextInputEditText mInputTheDescription=findViewById(R.id.description_task);
        mImageView = findViewById(R.id.image_view);


        Intent intent1 = getIntent();
        intent1.setType(intent1.ACTION_SEND);
        if(intent1 != null && intent1.getType() != null || intent1.getData() !=null) {
            Uri imageUri = (Uri) intent1.getParcelableExtra(Intent.EXTRA_STREAM);
            Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
            mImageView.setImageURI(imageUri);
        }
        mUploadImageButton.setOnClickListener(view -> {
            uploadPhoto();
        });
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

//                    // upload to s3
//                    // uploads the file
                Amplify.Storage.uploadFile(
                        taskTitle+".jpg",
                        file,
                        uploadSuccess ->{
                            Log.i(TAG, "upload image is succeed" + uploadSuccess.getKey());
                            runOnUiThread(() -> {
                                key = uploadSuccess.getKey();
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


                                    Intent intent = new Intent(getApplicationContext(), AllTasks.class);
                                    setResult(RESULT_OK,intent);
                                }
                            }
                        },
                                        error -> Log.e(TAG, "Error", error)
                                );
                            });
                        },
                        failure -> Log.e(TAG, "Uploaded failed.", failure));

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
    private void uploadPhoto() {

            intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE);
//          startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

    }
    private void displayImage() {
        Amplify.Storage.getUrl("image.jpg",
                success ->{
                    Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                    Log.i(TAG, "Successfully downloaded: " + success.getUrl());

                },
                error -> Log.e(TAG,  "Download Failure", error)
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

        switch(requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();
                Toast.makeText(getApplicationContext(),"Image Selected",Toast.LENGTH_SHORT).show();
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

}