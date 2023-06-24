package com.example.as1bipinneupane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditPost extends AppCompatActivity {

    EditText edtPost;
    Button updatePostBtn;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        edtPost = findViewById(R.id.edtPostContent);
        updatePostBtn = findViewById(R.id.editPostBtn);
        db = new DBHelper(this);
        onClickUpdateBtn();
    }

    public void onClickUpdateBtn(){
        updatePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String postContent = edtPost.getText().toString();
                String postID = intent.getStringExtra("postID");
                db.updatePost(postID,postContent);
            }
        });
    }
}