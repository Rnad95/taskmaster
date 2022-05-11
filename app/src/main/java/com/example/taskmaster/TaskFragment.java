package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {
//    private Button btnDetails;
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "body";
    private static final String ARG_PARAM3 = "state";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private String mBody;
    private String mState;

    public TaskFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String mTitle, String mBody,String mState) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mTitle);
        args.putString(ARG_PARAM2, mBody);
        args.putString(ARG_PARAM3, mState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);
            mBody = getArguments().getString(ARG_PARAM2);
            mState = getArguments().getString(ARG_PARAM3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task,container,false);
//        btnDetails = view.findViewById(R.id.details);
//        btnDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),TaskDetails.class);
//                startActivity(intent);
////                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_task, container, false);
        return view;
    }
}