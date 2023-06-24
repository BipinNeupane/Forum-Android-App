package com.example.as1bipinneupane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PostDetail extends AppCompatActivity {
    String username, postDate, post, postID;
    EditText medtComment;
    Button sendButton;
    PostDetailAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> name, comment, id, date;
    DBHelper DB;
    SharedPreferences preferences;
    //    PostDetailAdapter adapter;
    TextView txtgetusername, txtgetuserpost, txtgetuserpostdate, txtgetpostID;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        txtgetusername = findViewById(R.id.txtgetusername);
        txtgetuserpost = findViewById(R.id.txtgetuserpost);
        txtgetuserpostdate = findViewById(R.id.txtgetuserpostdate);
        txtgetpostID = findViewById(R.id.txtgetpostID);
        medtComment = findViewById(R.id.edtComment);
        sendButton = findViewById(R.id.BtncommentSend);

        preferences = getSharedPreferences("userLogData", MODE_PRIVATE);

        DB = new DBHelper(this);
        name = new ArrayList<>();
        comment = new ArrayList<>();
        date = new ArrayList<>();
        id = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewforComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostDetailAdapter(this, name, comment, id, date, DB);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        postDate = intent.getStringExtra("date");
        post = intent.getStringExtra("post");
        postID = intent.getStringExtra("postID");

        txtgetusername.setText(username);
        txtgetuserpost.setText(post);
        txtgetuserpostdate.setText(postDate);
//        function to display the data
        displadata();

        //        getting comment text


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmt = medtComment.getText().toString();
                String userID = preferences.getString("userID", null);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String comment_date = dateFormat.format(calendar.getTime());
                postComment(cmt, comment_date, userID, postID);
                adapter.notifyDataSetChanged();
                medtComment.setText("");
            }
        });

    }


    public void displadata() {
        Cursor cursor = DB.getComments(postID);
        int columnIndexOfuserName = cursor.getColumnIndex("firstName");
        int columnIndexOfComment = cursor.getColumnIndex("comment");
        int columnIndexOfDate = cursor.getColumnIndex("comment_date");
        int columnIndexOfID = cursor.getColumnIndex("comment_id");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No entry exists", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(columnIndexOfuserName));
                comment.add(cursor.getString(columnIndexOfComment));
                date.add(cursor.getString(columnIndexOfDate));
                id.add(cursor.getString(columnIndexOfID));
                Log.d("namelist", "namelist : " + name);
            }

        }
    }

    public void postComment(String commentTxt, String comment_date, String commentedBy, String forPost) {
//        if comment is empty
        if(commentTxt.equals("")){
            Toast.makeText(this, "Cannot Post Empty Comment", Toast.LENGTH_SHORT).show();
        }else {
            boolean isPosted = DB.postComment(commentTxt, comment_date, commentedBy, forPost);

            if (!isPosted) {
                Toast.makeText(this, "Comment not posted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Comment posted", Toast.LENGTH_SHORT).show();
            }
        }

    }


}

