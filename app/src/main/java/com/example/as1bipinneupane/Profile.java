package com.example.as1bipinneupane;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Profile extends Fragment {
        TextView mfirstname,mlastname,mcountry,mphone,memail,dateUpdated;
        Button updateProfilebtn, logoutBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getByID(view);
        setText(mfirstname,mlastname,mcountry,mphone,memail,dateUpdated);
        afterUpdateClick(view);
        logout();



        return view;
    }

    private void getByID(View view){
        mfirstname = view.findViewById(R.id.profileFname);
        mlastname = view.findViewById(R.id.profileLname);
        mcountry = view.findViewById(R.id.profileCountry);
        mphone = view.findViewById(R.id.profilePnumber);
        memail = view.findViewById(R.id.profileEmail);
        updateProfilebtn = view.findViewById(R.id.updateProfileBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        dateUpdated = view.findViewById(R.id.txtDateUpdated);
    }



    private void setText(TextView firstname,TextView lastname,TextView country,TextView phone,TextView email,TextView dateUpdated){
        SharedPreferences preferences = getActivity().getSharedPreferences("userLogData", Context.MODE_PRIVATE);
        mfirstname.setText(preferences.getString("firstName",null));
        mlastname.setText(preferences.getString("lastName",null));
        mcountry.setText(preferences.getString("country",null));
        mphone.setText(preferences.getString("phone",null));
        memail.setText(preferences.getString("email",null));
        dateUpdated.setText(preferences.getString("dateUpdated",null));
    }

    private void afterUpdateClick(View view){
    updateProfilebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

//            intent to UpdateProfile Activity
            Intent intent = new Intent(getActivity(), UpdateProfile.class);
            startActivity(intent);

        }
    });
    }

    // if user clicks logout button it sends to mainacitvity
    private void logout(){
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                intent to login page
                Intent toMainActivity = new Intent(getActivity(), MainActivity.class);
                startActivity(toMainActivity);
                getActivity().finish();

            }
        });
    }
}