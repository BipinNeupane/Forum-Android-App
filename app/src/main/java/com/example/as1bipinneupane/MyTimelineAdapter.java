package com.example.as1bipinneupane;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyTimelineAdapter extends RecyclerView.Adapter<MyTimelineAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> firstName, postContent, postDate, postID;
    DBHelper db;

    public MyTimelineAdapter(Context context, ArrayList<String> firstName, ArrayList<String> postContent, ArrayList<String> postDate, ArrayList<String> postID, DBHelper db) {
        this.context = context;
        this.firstName = firstName;
        this.postContent = postContent;
        this.postDate = postDate;
        this.postID = postID;
        this.db = db;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypoststemplate, parent, false);
        return new MyTimelineAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTimelineAdapter.MyViewHolder holder, int position) {
        holder.mFirstName.setText(String.valueOf(firstName.get(position)));
        holder.mPostContent.setText(String.valueOf(postContent.get(position)));
        holder.mPostDate.setText(String.valueOf(postDate.get(position)));
        holder.mPostID.setText(String.valueOf(postID.get(position)));
        holder.onClickDeleteBtn();
        holder.onClickEditBtn();
    }

    @Override
    public int getItemCount() {
        return firstName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mFirstName, mPostContent, mPostDate, mPostID;
        Button deletePostBtn,editPostBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mFirstName = itemView.findViewById(R.id.txtMyPostFname);
            mPostContent = itemView.findViewById(R.id.txtMyPostPost);
            mPostDate = itemView.findViewById(R.id.txtMyPostDate);
            mPostID = itemView.findViewById(R.id.txtMyPostPostID);
            deletePostBtn = itemView.findViewById(R.id.MyPostDeleteBtn);
            editPostBtn = itemView.findViewById(R.id.MyPostEditBtn);
        }


        public void onClickDeleteBtn() {
            deletePostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Post");
                    builder.setMessage("Are you sure you want to delete this post?");

// Set the positive button
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            deleting from the database
                            String postID = mPostID.getText().toString();
                            db.deletePost(postID);
                            Toast.makeText(context, "Post deleted", Toast.LENGTH_SHORT).show();
                        }
                    });

// Set the negative button
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when the user clicks on the No button
                        }
                    });

// Create and show the alert dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
        }

        public void onClickEditBtn(){
            editPostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String postContent = mPostContent.getText().toString();
                    String getPostID = mPostID.getText().toString();
                    Intent intent = new Intent(context, EditPost.class);
                    intent.putExtra("postContent",postContent);
                    intent.putExtra("postID",getPostID);
                    context.startActivity(intent);
                }
            });
        }


    }
}
