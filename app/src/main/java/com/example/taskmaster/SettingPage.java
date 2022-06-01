package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.List;

public class SettingPage extends AppCompatActivity {
    private static final String TAG = SettingPage.class.getSimpleName();
    public static final String USERNAME = "username";
    private String mTeamSelected="";
    private EditText mUsernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        Button btnSelect = findViewById(R.id.btn_select_team);
        mUsernameEditText = findViewById(R.id.edit_text_username_setting);
        Button btnSave = findViewById(R.id.btn_save);


//        try {
//            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i(TAG, "Initialized Amplify");
//        } catch (AmplifyException e) {
//            Log.e(TAG, "Could not initialize Amplify", e);
//        }

        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Spinner mTeamSpinnerText = findViewById(R.id.team_of_task_setting_page);
                mTeamSelected =  mTeamSpinnerText.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("teamSelect",mTeamSelected);
                startActivity(intent);
            }

        });


        /**
         * Edit the username
         */
        btnSave.setOnClickListener(view -> {
            Log.i(TAG, "Submit button clicked");
            if (mUsernameEditText.getText().toString().length() <= 20) {
                saveUsername();
            } else {
                Toast.makeText(this, "Add a shorter username", Toast.LENGTH_SHORT).show();
            }

            View view2 = this.getCurrentFocus();
            if (view2 != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
            }
        });

        mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "onTextChanged: the text is : " + charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG, "afterTextChanged: the final text is : " + editable.toString());
                if (!btnSave.isEnabled()) {
                    btnSave.setEnabled(true);
                }

                if (editable.toString().length() == 0) {
                    btnSave.setEnabled(false);
                }

            }
        });
    }



    private void saveUsername() {
        String username = mUsernameEditText.getText().toString();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putString(USERNAME, "@" + username);
        preferenceEditor.apply();
        Intent intent = new Intent();
        intent.putExtra("Username", username);
        Toast.makeText(this, "username Saved", Toast.LENGTH_SHORT).show();
    }

}