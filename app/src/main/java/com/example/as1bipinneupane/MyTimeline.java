package com.example.as1bipinneupane;

import android.content.Context;
import android.content.SharedPreferences;
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


public class MyTimeline extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> firstname,postContent,postDate,post_id;
    DBHelper DB;
    MyTimelineAdapter myTimelineAdapter;
    SharedPreferences preferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_timeline, container, false);

        DB = new DBHelper(getActivity());
        firstname = new ArrayList<>();
        postContent = new ArrayList<>();
        postDate = new ArrayList<>();
        post_id = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerviewforMyTimeline);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myTimelineAdapter = new MyTimelineAdapter(getActivity(),firstname,postContent,postDate,post_id,DB);
        recyclerView.setAdapter(myTimelineAdapter);
        preferences = getActivity().getSharedPreferences("userLogData", Context.MODE_PRIVATE);
        displadata();
        // Inflate the layout for this fragment
        return view;
    }

    //to display data in view holder
    private void displadata(){
        String userID = preferences.getString("userID",null);
        Cursor cursor = DB.getUserPost(userID);
        int columnIndexofName = cursor.getColumnIndex("firstName");
        int columnIndexopostContent = cursor.getColumnIndex("post");
        int columnIndexofpostDate = cursor.getColumnIndex("date_upload");
        int columnIndexofpostID = cursor.getColumnIndex("post_id");
        if (cursor.getCount()==0){
            Toast.makeText(getActivity(), "No entry exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while (cursor.moveToNext()){
                firstname.add(cursor.getString(columnIndexofName));
                postContent.add(cursor.getString(columnIndexopostContent));
                postDate.add(cursor.getString(columnIndexofpostDate));
                post_id.add(cursor.getString(columnIndexofpostID));
            }
        }

    }

}