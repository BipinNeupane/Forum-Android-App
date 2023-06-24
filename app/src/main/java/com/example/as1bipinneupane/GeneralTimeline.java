package com.example.as1bipinneupane;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class GeneralTimeline extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> username,postcontent,date,post_id;
    DBHelper DB;
    GeneralTimelineAdapterForUser adapterForUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_timeline, container, false);
        // Inflate the layout for this


        DB = new DBHelper(getActivity());
        username = new ArrayList<>();
        postcontent = new ArrayList<>();
        post_id = new ArrayList<>();
        date = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterForUser = new GeneralTimelineAdapterForUser(getActivity(),username,postcontent,date,post_id,DB);
        recyclerView.setAdapter(adapterForUser);
        displadata();

        return view;
    }

//to display data in view holder
    private void displadata(){
        Cursor cursor = DB.getForumPosts();
        int columnIndexofName = cursor.getColumnIndex("firstName");
        int columnIndexofpostID = cursor.getColumnIndex("post_id");
        Log.d("columnIndexofFname", "The column Index is :" + columnIndexofName);
            if (cursor.getCount()==0){
            Toast.makeText(getActivity(), "No entry exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while (cursor.moveToNext()){
                username.add(cursor.getString(columnIndexofName));
                postcontent.add(cursor.getString(1));
                date.add(cursor.getString(2));
                post_id.add(cursor.getString(columnIndexofpostID));
            }
        }

    }
}