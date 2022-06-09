package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mUsernameText;
    Handler handler;
    TaskAdapter adapter;
    Bundle bundle1;
    String teamData = "Reading";
    RecyclerView allTaskRecyclerView;
    List<Task> allTasks;
    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;
    TextView mRewardTextView;


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
    private Button mBtnAds;
    private AdRequest adRequest;
    private AdRequest adRequest1;
    private Button mRewardedADSBtn;
    //    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-6537723123041499/8805360972");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        AdView mAdView = findViewById(R.id.adView);
        adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);


        authSession();
        Button mAddTaskButton = findViewById(R.id.add_task);
        Button mAllTaskButton = findViewById(R.id.all_task);

        mAddTaskButton.setOnClickListener(mClickListener);
        mAllTaskButton.setOnClickListener(mClickListener2);
        mUsernameText = findViewById(R.id.txt_username);
//        Interstitial ADS
        mBtnAds = findViewById(R.id.btn_ads);
        mBtnAds.setOnClickListener(view -> {
            setmInterstitialAd();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        });

//      Rewarded ADS
        mRewardedADSBtn = findViewById(R.id.btn_reward_ads);
        mRewardedADSBtn.setOnClickListener(viw -> {
            setmRewardedADSBtn();
            if (mRewardedAd != null) {
                Activity activityContext = MainActivity.this;
                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        Log.d(TAG, "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                        mRewardTextView = findViewById(R.id.reward_textView);
//                        Toast.makeText(activityContext, "Congrats, you earn a reward :)", Toast.LENGTH_SHORT).show();
                        mRewardTextView.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.");
            }
        });



        /**
         * Create three teams
         */
//        Team team =Team.builder()
//                .name("Sport")
//                .build();
//
//        Amplify.API.mutate(ModelMutation.create(team),
//                successSaveToAPI -> Log.i(TAG,"Saved item: "+ successSaveToAPI.getData().getName()),
//                error -> Log.e(TAG,"Could not save to API" + error)
//        );

        handler = new Handler(Looper.getMainLooper(), msg -> {
            allTaskRecyclerView = findViewById(R.id.show_recycler_view);
            allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new TaskAdapter(
                    this.allTasks, position -> {
                Intent intent = new Intent(getApplicationContext(), TaskDetails.class);
                intent.getExtras();
                intent.putExtra("SpecificData", this.allTasks.get(position).getTitle());
                intent.putExtra("stateData", this.allTasks.get(position).getStatus());
                intent.putExtra("bodyData", this.allTasks.get(position).getDescription());
                intent.putExtra("latitude",this.allTasks.get(position).getLatitude());
                intent.putExtra("longitude",this.allTasks.get(position).getLonitude());
                intent.putExtra("imageKey",allTasks.get(position).getImageKey());

                startActivity(intent);
            });
            allTaskRecyclerView.setAdapter(adapter);

            return true;
        });


        Button addBtn = findViewById(R.id.add_task);
        addBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent);
        });

    }


    //    @Override
    public void onTaskListener(int position) {
        Intent intent = new Intent(getApplicationContext(), TaskDetails.class);
        System.out.println("***************** POSITION ==>" + position);
        intent.putExtra("SpecificData", allTasks.get(position).getTitle());
        intent.putExtra("stateData", allTasks.get(position).getStatus());
        intent.putExtra("bodyData", allTasks.get(position).getDescription());
        intent.putExtra("latitude",allTasks.get(position).getLatitude());
        intent.putExtra("logitude",allTasks.get(position).getLonitude());
        intent.putExtra("imageKey",allTasks.get(position).getImageKey());

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
            case R.id.action_logout:
                logout();
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_share:
                // Create the text message with a string.
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I want to share");
            sendIntent.setType("image/*");

            // Try to invoke the intent.
            try {
                startActivity(sendIntent);
            } catch (ActivityNotFoundException e) {
                // Define what your app should do if no activity can handle the intent.
            }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    authSession();
                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );
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
        mUsernameText.setText(sharedPreferences.getString(SettingPage.USERNAME, "Username"));

    }

    private void getTasks() {

        if (getIntent().getExtras() != null) {
            bundle1 = getIntent().getExtras();
            teamData = bundle1.getString("teamSelect");
            System.out.println("******************************* TEAM DATA =>" + teamData);
        }
        Amplify.API.query(ModelQuery.list(Team.class),
                success -> {
                    List<Task> allTasks = new ArrayList<>();
                    for (Team team : success.getData().getItems()) {
                        if (team.getName().equals(teamData)) {
                            for (Task task : team.getTasks()) {
                                if (team.getId().equals(task.getTeamTasksId())) {
                                    Log.i(TAG, "Success If ===> the Task Title is " + task.getTitle());
                                    allTasks.add(task);
                                }
                            }
                        }
                    }
                    this.allTasks = allTasks;
                    Log.i(TAG, "Tasks => " + allTasks);
                    Bundle bundle = new Bundle();
                    bundle.putString("TeamTasks", allTasks.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                    runOnUiThread(() -> {
                        allTaskRecyclerView.setAdapter(adapter);
                    });
                },
                error -> Log.e(TAG, "Error", error)
        );
    }

    /**
     * Get Data from the aPI
     */
//
//    private void getTasks() {
//
//        Amplify.API.query(
//                ModelQuery.list(Task.class),
//                success -> {
//                    allTasks = new ArrayList<>();
//
//                    if (success.hasData()) {
//                        for (Task task : success.getData()) {
//                            allTasks.add(task);
//                        }
//                    }
//                    Log.i(TAG, "Tasks => " + allTasks);
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("tasksList", success.toString());
//
//                    Message message = new Message();
//                    message.setData(bundle);
//
//                    handler.sendMessage(message);
//
//                    runOnUiThread(() -> {
//                        allTaskRecyclerView.setAdapter(adapter);
//                    });
//                },
//                error -> Log.e(TAG, error.toString(), error)
//        );
//
//    }
//
    private void authSession() {
        Amplify.Auth.fetchAuthSession(
                result -> Log.i(TAG, "Auth Session" + result.toString()),
                error -> Log.e(TAG, error.toString())
        );
    }

    private void setmInterstitialAd(){

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "Interstitial => onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

    }

    private void setmRewardedADSBtn(){
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad was dismissed.");
                                mRewardedAd = null;
                            }
                        });
                    }
                });
    }
}
