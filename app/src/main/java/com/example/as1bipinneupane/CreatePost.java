package com.example.as1bipinneupane;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CreatePost extends Fragment {

    private EditText mpostContent;
    private Button submitBtn;
    private SharedPreferences preferences;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        // Inflate the layout for this fragment
        getByID(view);
        preferences = getActivity().getSharedPreferences("userLogData", Context.MODE_PRIVATE);
        db = new DBHelper(getActivity());
        onClick();
        return view;
    }

    private void getByID(View view) {
        mpostContent = view.findViewById(R.id.edtPost);
        submitBtn = view.findViewById(R.id.createPostBtn);
    }

    private void onClick() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateUpload = dateFormat.format(calendar.getTime());
                String postContent = mpostContent.getText().toString();
                String userID = preferences.getString("userID", null);

                db.postForum(postContent, dateUpload, userID);

                Toast.makeText(getActivity(), "Post uploaded", Toast.LENGTH_SHORT).show();
                Intent toGeneralActivity = new Intent(getActivity(), FragmentBinder.class);
                startActivity(toGeneralActivity);
                getActivity().finish();


            }
        });
    }
}