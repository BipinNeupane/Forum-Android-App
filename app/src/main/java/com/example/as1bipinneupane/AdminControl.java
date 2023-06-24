package com.example.as1bipinneupane;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminControl extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> firstname,lastname,email,userID;
    DBHelper db;
    AdminControlAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_control, container, false);

        db = new DBHelper(getActivity());
        firstname = new ArrayList<>();
        lastname = new ArrayList<>();
        email = new ArrayList<>();
        userID = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerviewForAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdminControlAdapter(getActivity(),firstname,lastname,email,userID,db);
        recyclerView.setAdapter(adapter);
        displadata();
        // Inflate the layout for this fragment
        return view;
    }


    //to display data in view holder
    private void displadata(){
        Cursor cursor = db.getUsers();
        int columnIndexofName = cursor.getColumnIndex("firstName");
        int columnIndexolName = cursor.getColumnIndex("lastName");
        int columnIndexofemail = cursor.getColumnIndex("email");
        int columnIndexofuserID = cursor.getColumnIndex("user_id");
        if (cursor.getCount()==0){
            Toast.makeText(getActivity(), "No entry exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while (cursor.moveToNext()){
                firstname.add(cursor.getString(columnIndexofName));
                lastname.add(cursor.getString(columnIndexolName));
                email.add(cursor.getString(columnIndexofemail));
                userID.add(cursor.getString(columnIndexofuserID));
            }
        }

    }

}